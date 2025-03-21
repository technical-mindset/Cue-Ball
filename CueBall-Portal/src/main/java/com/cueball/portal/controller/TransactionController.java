//package com.dawateislami.centraldonation.admin.controller;
//
//import com.dawateislami.centraldonation.admin.service.TransactionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//@RestController
//@RequestMapping(value = "/transaction")
//public class TransactionController {
//    @Autowired
//    private TransactionService service;
//
//    @GetMapping(value = "/viewAll")
//    public ModelAndView findAllView(
//            @RequestParam(value = "startDate",required = false, defaultValue = "NaN") String startDate,
//            @RequestParam(value = "endDate",required = false, defaultValue = "NaN") String endDate,
//            @RequestParam(value = "operatingUnit",required = false, defaultValue = "0") Integer operatingUnit,
//            @RequestParam(value = "campaign",required = false, defaultValue = "0") Integer campaign,
//            @RequestParam(value = "bank",required = false, defaultValue = "0") Integer bank,
//            @RequestParam(value = "successIndicator",required = false, defaultValue = "0") Integer successIndicator,
//            @RequestParam(value = "advertisement",required = false, defaultValue = "0") Integer advertisement,
//            @RequestParam(value = "search",required = false) String search,
//            @RequestParam(value = "enable",required = false) Integer enable,
//            @RequestParam(value = "ps", required = false) Integer pageSize,
//            @RequestParam(value = "pn", required = false) Integer pageNumber,
//            @RequestParam(value = "ajax", required = false, defaultValue = "false") boolean ajax) {
//        return service.findFindAllView(startDate, endDate, operatingUnit, campaign, bank, successIndicator, advertisement,search,enable,pageSize,pageNumber,ajax);
//    }
//}
