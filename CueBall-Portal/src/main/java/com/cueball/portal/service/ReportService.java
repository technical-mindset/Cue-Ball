package com.cueball.portal.service;


import com.cueball.portal.utils.Constants;
import com.cueballdb.model.Booking;
import com.cueballdb.repository.BookingRepository;
import com.cueballdb.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReportService {
    @Autowired
    private BookingRepository repository;

    @Autowired
    private UserRepository userRepository;

    public void generateReport(String search, Integer enable, Integer roomId, Integer roomCategoryId, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"Cue-Ball-Report.xlsx\"");

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Donations");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            sheet = createRow(sheet, headerCellStyle);
            CreationHelper createHelper = workbook.getCreationHelper();
            int rowNum = 1;

            /**  Retrieving the Transactions according to user permissions  */
            List<Booking> bookings = null;
                bookings = this.repository.findAllByFilterExcel(search, enable, roomId, roomCategoryId, startDate, endDate);

                if (bookings == null || bookings.isEmpty()) {
                    throw new RuntimeException("No Report found");
                }
                for (Booking report : bookings) {
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
                    Object[] cellValues = ReportService.cellValues(report);
                    for (int i = 0; i < cellValues.length; i++) {
                        if (cellValues[i] != null) {
                            row.createCell(i + 2).setCellValue(cellValues[i].toString());
                        }
                    }
                }

                /**  Loop over the Sheet to auto-size the columns  */
                for (int i = 0; i < Constants.COLUMNS.length; i++) {
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
    public static Object[] cellValues(Booking booking){
        /** Calculation of total-time and total-charges */
        Date in = booking.getTimeIn();
        Date out = booking.getTimeOut();

        if (booking.getCheckIn() != null && booking.getCheckIn().getTime() > booking.getTimeIn().getTime()) {
            in = booking.getCheckIn();
        }
        if (booking.getCheckOut() != null && booking.getCheckOut().getTime() > booking.getTimeOut().getTime()) {
            out = booking.getCheckOut();
        }
        double totalTime = timeCalculation(in, out);
        // Array of cell values
        Object[] cellValues = {
                booking.getTitle(),
                booking.getCustomer().getName(),
                booking.getCustomer().getEmail(),
                booking.getCustomer().getContact(),
                booking.getRoom().getRoomCategory().getName(),
                booking.getRoom().getName() + '(' + booking.getRoom().getTitle() + ')',
                booking.getTimeIn(),
                booking.getTimeOut(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                totalTime, //Total-Time calculation
                Math.ceil(booking.getRoom().getCharges()),
                totalTime * booking.getRoom().getCharges() // total charges calculation
        };
        return cellValues;
    }

    public Sheet createRow(Sheet sheet, CellStyle cellStyle){
        Row row = sheet.createRow(0);
        for (int i = 0; i < Constants.COLUMNS.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(Constants.COLUMNS[i]);
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    private static double timeCalculation(Date in, Date out){
        long diffInMillies = Math.abs(out.getTime() - in.getTime());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
        return minutes;

    }

}
