
package com.cueball.portal.controller;


import com.cueball.portal.dto.InventoryDTO;
import com.cueball.portal.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class InventoryController {

    @Autowired
    private InventoryService service;


    @RequestMapping(value="/inventories/{id}", method = RequestMethod.GET)
    public InventoryDTO getById(@PathVariable("id")int id){
        return service.findById(id);
    }


    @Transactional
    @GetMapping(value = "/inventories/addUpdate")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ModelAndView getView(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes ) {
        return service.getView(id, redirectAttributes);
    }


    @Transactional
    @PostMapping(value = "/inventories/addUpdate")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ModelAndView addUpdate(@ModelAttribute("dto") @Valid InventoryDTO inventoryDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        return service.addUpdate(inventoryDTO, result, redirectAttributes);

    }


    @GetMapping(value = "/inventories/viewAll")
    public ModelAndView findAllView(
            @RequestParam(value = "search",required = false )String search,
            @RequestParam(value = "enable",required = false )Integer enable,
            @RequestParam(value = "variant",required = false, defaultValue = "0") Integer variantId,
            @RequestParam(value = "restaurant",required = false, defaultValue = "0") Integer restaurantId,
            @RequestParam(value = "ps",required = false ) Integer pageSize,
            @RequestParam(value = "pn",required = false ) Integer pageNumber,
            @RequestParam(value = "ajax",required = false, defaultValue = "false") boolean ajax) {
        return service.findFindAllView(search, enable, variantId, restaurantId, pageSize, pageNumber, ajax);
    }


    @GetMapping(value = "/tuckShop/subMenu")
    public ModelAndView getSubMenu(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "ps",required = false ) Integer pageSize,
            @RequestParam(value = "pn",required = false ) Integer pageNumber,
            @RequestParam(value = "ajax",required = false, defaultValue = "false") boolean ajax) {
        return service.getSubMenu(id, pageSize, pageNumber, ajax);
    }

}