//package com.dawateislami.centraldonation.admin.controller;
//
//
//import com.dawateislami.centraldonation.admin.dto.BankDTO;
//import com.dawateislami.centraldonation.admin.dto.CurrencyDTO;
//import com.dawateislami.centraldonation.admin.service.RoomCategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping(value = "/currency")
//public class CurrencyController {
//
//    @Autowired
//    RoomCategoryService service;
//
//
//    @GetMapping(value = "/viewAll")
//    public ModelAndView findAllView(
//            @RequestParam(value = "lang",required = false )String lang,
//            @RequestParam(value = "search",required = false )String search,
//            @RequestParam(value = "enable",required = false )Integer enable,
//            @RequestParam(value = "ps",required = false ) Integer pageSize,
//            @RequestParam(value = "pn",required = false ) Integer pageNumber,
//            @RequestParam(value = "ajax",required = false, defaultValue = "false") boolean ajax) {
//        return service.findFindAllView(lang,search,enable,pageSize,pageNumber,ajax);
//    }
//
//    @Transactional
//    @GetMapping(value = "/addUpdate")
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
//    public ModelAndView getView(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes ) {
//        return this.service.getView(id, redirectAttributes);
//    }
//
//    @Transactional
//    @PostMapping(value = "/addUpdate")
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
//    public ModelAndView addUpdate(@Valid @ModelAttribute("dto") CurrencyDTO currencyDTO, BindingResult result, RedirectAttributes redirectAttributes) {
//        return this.service.addUpdate(currencyDTO, result, redirectAttributes);
//    }
//
//}
