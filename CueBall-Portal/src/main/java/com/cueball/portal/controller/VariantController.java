
package com.cueball.portal.controller;


import com.cueball.portal.dto.InventoryCategoryDTO;
import com.cueball.portal.dto.VariantDTO;
import com.cueball.portal.service.InventoryCategoryService;
import com.cueball.portal.service.VariantService;
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
@RequestMapping("/variant")
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
public class VariantController {

    @Autowired
    private VariantService service;


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public VariantDTO getById(@PathVariable("id")int id){
        return service.findById(id);
    }


    @Transactional
    @GetMapping(value = "/addUpdate")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ModelAndView getView(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes ) {
        return service.getView(id, redirectAttributes);
    }


    @Transactional
    @PostMapping(value = "/addUpdate")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ModelAndView addUpdate(@ModelAttribute("dto") @Valid VariantDTO variantDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        return service.addUpdate(variantDTO, result, redirectAttributes);

    }


    @GetMapping(value = "/viewAll")
    public ModelAndView findAllView(
            @RequestParam(value = "search",required = false )String search,
            @RequestParam(value = "enable",required = false )Integer enable,
            @RequestParam(value = "ps",required = false ) Integer pageSize,
            @RequestParam(value = "pn",required = false ) Integer pageNumber,
            @RequestParam(value = "ajax",required = false, defaultValue = "false") boolean ajax) {
        return service.findFindAllView(search,enable,pageSize,pageNumber,ajax);
    }

}