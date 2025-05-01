package com.cueball.portal.controller;


import com.cueball.portal.dto.BookingDTO;
import com.cueball.portal.service.CustomerCheckOutService;
import com.cueball.portal.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
public class CustomerController {

    @Autowired
    CustomerCheckOutService service;

    @Autowired
    ReportService report;

    @GetMapping(value = "/viewAll")
    public ModelAndView findAllView(
            @RequestParam(value = "startDate",required = false, defaultValue = "NaN") String startDate,
            @RequestParam(value = "endDate",required = false, defaultValue = "NaN") String endDate,
            @RequestParam(value = "search",required = false )String search,
            @RequestParam(value = "enable",required = false )Integer enable,
            @RequestParam(value = "room",required = false, defaultValue = "0") Integer roomId,
            @RequestParam(value = "roomCategory",required = false, defaultValue = "0") Integer roomCategoryId,
            @RequestParam(value = "ps",required = false ) Integer pageSize,
            @RequestParam(value = "pn",required = false ) Integer pageNumber,
            @RequestParam(value = "ajax",required = false, defaultValue = "false") boolean ajax) {
        return service.findFindAllView(search, enable, roomId, roomCategoryId, startDate, endDate, pageSize, pageNumber, ajax);
    }

    @PostMapping("/report")
    public void generateReport(
            @RequestParam(value = "startDate",required = false, defaultValue = "NaN") String startDate,
            @RequestParam(value = "endDate",required = false, defaultValue = "NaN") String endDate,
            @RequestParam(value = "search",required = false )String search,
            @RequestParam(value = "enable",required = false )Integer enable,
            @RequestParam(value = "room",required = false, defaultValue = "0") Integer roomId,
            @RequestParam(value = "roomCategory",required = false, defaultValue = "0") Integer roomCategoryId,
            HttpServletRequest request, HttpServletResponse response){
        System.out.println("In ------------- Generate Report Controller -----------------------");
        this.report.generateReport(search, enable, roomId, roomCategoryId, startDate, endDate, request, response);
    }



}

