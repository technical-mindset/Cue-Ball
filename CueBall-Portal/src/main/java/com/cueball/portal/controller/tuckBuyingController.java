
package com.cueball.portal.controller;


import com.cueball.portal.response.GenericResponse;
import com.cueball.portal.response.TuckBuyingMenu;
import com.cueball.portal.service.InventoryService;
import com.cueball.portal.service.TuckBuyingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
public class tuckBuyingController {

    @Autowired
    private TuckBuyingService service;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/tuckShop/subMenu/checkout", consumes = "application/json")
    @ResponseBody
    public GenericResponse submitQurbani(
            @RequestBody List<TuckBuyingMenu> tuckItemJson
    ) {
        return service.Checkout(tuckItemJson);
    }


    @GetMapping(value = "/tuckShopReport/viewAll")
    public ModelAndView findAllView(
            @RequestParam(value = "onlyStartDate",required = false, defaultValue = "NaN") String startDate,
            @RequestParam(value = "onlyEndDate",required = false, defaultValue = "NaN") String endDate,
            @RequestParam(value = "search",required = false )String search,
            @RequestParam(value = "enable",required = false )Integer enable,
            @RequestParam(value = "inventory",required = false, defaultValue = "0") Integer inventoryId,
            @RequestParam(value = "ps",required = false ) Integer pageSize,
            @RequestParam(value = "pn",required = false ) Integer pageNumber,
            @RequestParam(value = "ajax",required = false, defaultValue = "false") boolean ajax) {
        return service.findFindAllView(search, enable, inventoryId, startDate, endDate, pageSize, pageNumber, ajax);
    }

    @PostMapping("/tuckShop/report")
    public void generateReport(
            @RequestParam(value = "onlyStartDate",required = false, defaultValue = "NaN") String startDate,
            @RequestParam(value = "onlyEndDate",required = false, defaultValue = "NaN") String endDate,
            @RequestParam(value = "search",required = false )String search,
            @RequestParam(value = "enable",required = false )Integer enable,
            @RequestParam(value = "inventory",required = false, defaultValue = "0") Integer inventoryId,
            HttpServletRequest request, HttpServletResponse response){
        System.out.println("In ------------- Generate Report Controller -----------------------");
        this.service.generateReport(search, enable,inventoryId, startDate, endDate, request, response);
    }
}