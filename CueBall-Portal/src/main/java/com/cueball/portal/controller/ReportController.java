//package com.dawateislami.centraldonation.admin.controller;
//
//import com.dawateislami.centraldonation.admin.service.ReportService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//@RequestMapping("/report")
//public class ReportController {
//    @Autowired
//    private ReportService service;
//
//    @PostMapping("/generate")
//    public void generateReport(
//            @RequestParam(value = "startDate",required = false, defaultValue = "NaN") String startDate,
//            @RequestParam(value = "endDate",required = false, defaultValue = "NaN") String endDate,
//            @RequestParam(value = "operatingUnit",required = false, defaultValue = "0") Integer operatingUnit,
//            @RequestParam(value = "campaign",required = false, defaultValue = "0") Integer campaign,
//            @RequestParam(value = "bank",required = false, defaultValue = "0") Integer bank,
//            @RequestParam(value = "successIndicator",required = false, defaultValue = "0") Integer successIndicator,
//            @RequestParam(value = "advertisement",required = false, defaultValue = "0") Integer advertisement,
//            @RequestParam(value = "search",required = false) String search,
//            @RequestParam(value = "enable",required = false) Integer enable,
//            HttpServletRequest request, HttpServletResponse response){
//        System.out.println("In ------------- Generate Report Controller -----------------------");
//       this.service.generateReport(startDate, endDate, operatingUnit, campaign, bank, successIndicator, advertisement, search, enable, request, response);
//    }
//}
