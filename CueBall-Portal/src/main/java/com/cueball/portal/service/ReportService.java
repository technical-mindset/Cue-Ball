//package com.dawateislami.centraldonation.admin.service;
//
//import com.dawateislami.centraldonation.admin.utils.Constants;
//import com.dawateislami.centraldonationdb.model.Transaction;
//import com.dawateislami.centraldonationdb.model.User;
//import com.dawateislami.centraldonationdb.repository.TransactionRepository;
//import com.dawateislami.centraldonationdb.repository.UserRepository;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.List;
//
//@Service
//public class ReportService {
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public void generateReport(String startDate, String endDate, Integer operatingUnit, Integer campaign, Integer bank, Integer successIndicator, Integer advertisement, String search, Integer enable,
//                               HttpServletRequest request, HttpServletResponse response) {
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "attachment; filename=\"DonationList.xlsx\"");
//
//        Workbook workbook = null;
//        try {
//            workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("Donations");
//
//            Font headerFont = workbook.createFont();
//            headerFont.setBold(true);
//            headerFont.setFontHeightInPoints((short) 14);
//            CellStyle headerCellStyle = workbook.createCellStyle();
//            headerCellStyle.setFont(headerFont);
//
//            sheet = createRow(sheet, headerCellStyle);
//            CreationHelper createHelper = workbook.getCreationHelper();
//            int rowNum = 1;
//
//            /**  Retrieving the Transactions according to user permissions  */
//            List<Transaction> transactions = null;
//            if (getUser().getId() != 1) {
//                transactions = this.transactionRepository.findAllByFiltersForReport(startDate, endDate, operatingUnit, campaign, bank, successIndicator, advertisement, getUser().getOperatingUnit(), search, enable);
//            } else {
//                transactions = this.transactionRepository.findAllByFiltersForReport(startDate, endDate, operatingUnit, campaign, bank, successIndicator, advertisement, null, search, enable);
//            }
//
//
//            if (transactions == null || transactions.isEmpty()) {
//                throw new RuntimeException("No transactions found");
//            }
//            for (Transaction transaction : transactions) {
//                if (transaction == null) { throw new RuntimeException("Transaction is null"); }
//
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(transaction.getId());
//
//                // Date formatter
//                CellStyle cellStyle = workbook.createCellStyle();
//                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm"));
//                Cell cell = row.createCell(1);
//                cell.setCellValue(transaction.getCreatedDate());
//                cell.setCellStyle(cellStyle);
//                //- x - x - x - x - x - x - x - x -
//
//                /**  Loop over the cellValues array and populate cells  */
//                Object[] cellValues = ReportService.cellValues(transaction);
//                for (int i = 0; i < cellValues.length; i++) {
//                    if (cellValues[i] != null) {
//                        row.createCell(i + 2).setCellValue(cellValues[i].toString());
//                    }
//                }
//            }
//
//            /**  Loop over the Sheet to auto-size the columns  */
//            for (int i = 0; i < Constants.COLUMNS.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            /** Output the workbook to response */
//            try (OutputStream outputStream = response.getOutputStream()) {
//                workbook.write(outputStream);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException("Error writing workbook to output stream");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error generating report: ");
//        }
//        finally {
//            if (workbook != null) {
//                try {
//                    workbook.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    /** Mapping values into the Object type array */
//    public static Object[] cellValues(Transaction transaction){
//        // Array of cell values
//        Object[] cellValues = {
//                transaction.getDonorProfile().getName(),
//                transaction.getDonorProfile().getEmail(),
//                transaction.getDonorProfile().getContact(),
//                transaction.getType(),
//                transaction.getSuccessIndicator() == 0 ? "UNSUCCESS" : "SUCCESS",
//                transaction.getOrderId(),
//                transaction.getBank().getName(),
//                transaction.getCampaign().getTitle(),
//                transaction.getOperatingUnit().getTitle(),
//                transaction.getIsoCurrencyCode(),
//                transaction.getCountryCode(),
//                transaction.getActualAmount(),
//                transaction.getConvertedAmount(),
//                transaction.getWithTaxAmount(),
//                transaction.getMarketingSource(),
//                transaction.getAdvertisement() == 1 ? true : false,
//                transaction.getBankTransactionId(),
//                transaction.getBankTransactionStatus(),
//                transaction.getBankDescription(),
//                transaction.getBankResponseCode()
//        };
//        return cellValues;
//    }
//
//    public Sheet createRow(Sheet sheet, CellStyle cellStyle){
//        Row row = sheet.createRow(0);
//        for (int i = 0; i < Constants.COLUMNS.length; i++) {
//            Cell cell = row.createCell(i);
//            cell.setCellValue(Constants.COLUMNS[i]);
//            cell.setCellStyle(cellStyle);
//        }
//        return sheet;
//    }
//
//    public User getUser(){
//        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByUsername(auth.getName());
//        return user;
//    }
//
//
//
//}
