package com.cueball.portal.service;

import com.cueball.portal.dto.tuckBuyingDTO;
import com.cueball.portal.response.GenericResponse;
import com.cueball.portal.response.TuckBuyingMenu;
import com.cueball.portal.response.TuckBuyingResponse;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.*;
import com.cueballdb.repository.*;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class TuckBuyingService extends BaseService<TucBuying, tuckBuyingDTO, TuckBuyingRepository> {


    @Autowired
    private InventoryRepository inventoryRepository;

    public TuckBuyingService(TuckBuyingRepository repository) {
        super(repository);
    }

    @Override
    public tuckBuyingDTO mapEntityToDto(TucBuying entity) {
        tuckBuyingDTO dto = new tuckBuyingDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setCreatedAt(entity.getCreatedAt().getTime());
        dto.setModifiedAt(entity.getModifiedAt().getTime());
        return dto;
    }

    @Override
    public TucBuying mapDtoToEntity(tuckBuyingDTO dto) {
        TucBuying entity = new TucBuying();

        BeanUtils.copyProperties(dto, entity);

        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
            entity.setCreatedAt(new Date (dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date ( System.currentTimeMillis()));
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }

    /** SAVE TUCK PURCHASE RECORD */
    public TuckBuyingResponse Checkout(List <TuckBuyingMenu> tuckItemJson){
        TuckBuyingResponse tuckBuyingResponse = new TuckBuyingResponse(GenericResponse.SUCCESS);
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_TUCK_SHOP_SUB_MENU_VIEW_ALL);
        try{



            Integer menuItemsEntries = tuckItemJson.size();

            if(menuItemsEntries != null && menuItemsEntries > 0){
                for (int i = 0; i < menuItemsEntries; i++) {

                    TucBuying tucBuying = new TucBuying();

                    tucBuying.setCustomerId("0");
                    tucBuying.setInventoryId(tuckItemJson.get(i).getId().toString());
                    tucBuying.setInventoryName(tuckItemJson.get(i).getName());
                    tucBuying.setPrice(tuckItemJson.get(i).getActualPrice());
                    tucBuying.setQuantity(tuckItemJson.get(i).getQuantity());
                    tucBuying.setCreatedAt(new Date ( System.currentTimeMillis()));
                    tucBuying.setModifiedAt(new Date ( System.currentTimeMillis()));
                    tucBuying.setEnable(true);
                    tucBuying.setCreatedBy(1);
                    tucBuying.setModifyBy(1);
                    this.repository.save(tucBuying);

                    Inventory inventory = inventoryRepository.findById(tuckItemJson.get(i).getId()).get();
                    if(inventory.getQuantity()  > 0){
                        inventory.setQuantity(inventory.getQuantity() - 1);
                        inventoryRepository.save(inventory);
                    }

                }


            }

            tuckBuyingResponse.setStatus(200);
            tuckBuyingResponse.setMessage("Purchase Successful");

        }
        catch(Exception e){
            System.out.println("Tuck::HomeController::PostCall:: Error" + e);
            tuckBuyingResponse.setStatus(500);
            tuckBuyingResponse.setMessage("something went wrong !");
        }

        return tuckBuyingResponse;
    }

    public ModelAndView findFindAllView(String search ,Integer enable, Integer inventoryId, String startDate, String endDate, Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_TUCK_SHOP_REPORT_VIEW_ALL);

        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<TucBuying> lists = repository.findAllByFilterReport(search, enable, inventoryId.toString(), startDate, endDate, (pageNumber-1)*pageSize, pageSize, count);
        List<tuckBuyingDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());


        List<Inventory> inventoryList = this.inventoryRepository.findAllByEnableTrue();

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_TUCK_SHOP_REPORT_VIEW_ALL_DETAIL);
        }

        mav.addObject("inventoryList",inventoryList);
        mav.addObject("inventoryFilter",true);
        mav.addObject("toOnlyDateFilter",true);
        mav.addObject("fromOnlyDateFilter",true);
        mav.addObject("reportUrl", Constants.RA_BASE_URL + Constants.PORT);
        mav.addObject("reportBasePath", "/tuckShop/report");
        mav.addObject(Constants.EXTRA_FILTERS,true);
        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }

    public void generateReport(String search, Integer enable,Integer inventoryId, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"TuckShop-Report.xlsx\"");

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Cue Ball");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            sheet = createRow(sheet, headerCellStyle);
            CreationHelper createHelper = workbook.getCreationHelper();
            int rowNum = 1;

            /**  Retrieving the Transactions according to user permissions  */
            List<TucBuying> tucBuyings = null;
            tucBuyings = this.repository.findAllByFilterExcel(search, enable, inventoryId.toString(), startDate, endDate);

            if (tucBuyings == null || tucBuyings.isEmpty()) {
                throw new RuntimeException("No Report found");
            }
            for (TucBuying report : tucBuyings) {
                if (report == null) {
                    throw new RuntimeException("Report is null");
                }

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(report.getId());

                // Date formatter
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm"));
                Cell cell = row.createCell(1);
                cell.setCellValue(report.getCreatedAt());
                cell.setCellStyle(cellStyle);
                //- x - x - x - x - x - x - x - x -

                /**  Loop over the cellValues array and populate cells  */
                Object[] cellValues = cellValues(report);
                for (int i = 0; i < cellValues.length; i++) {
                    if (cellValues[i] != null) {
                        row.createCell(i + 2).setCellValue(cellValues[i].toString());
                    }
                }
            }

            /**  Loop over the Sheet to auto-size the columns  */
            for (int i = 0; i < Constants.COLUMNS_TUCK_BUYING.length; i++) {
                sheet.autoSizeColumn(i);
            }

            /** Output the workbook to response */
            try (OutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error writing workbook to output stream");
            }

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error generating report: ");
        }
        finally{
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Mapping values into the Object type array */
    public static Object[] cellValues(TucBuying tucBuying){
        /** Calculation of total-time and total-charges */
        // Array of cell values
        Object[] cellValues = {
                tucBuying.getInventoryName(),
                tucBuying.getQuantity(),
                tucBuying.getPrice(),
        };
        return cellValues;
    }

    public Sheet createRow(Sheet sheet, CellStyle cellStyle){
        Row row = sheet.createRow(0);
        for (int i = 0; i < Constants.COLUMNS_TUCK_BUYING.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(Constants.COLUMNS_TUCK_BUYING[i]);
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }
}



