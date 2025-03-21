//package com.dawateislami.centraldonation.admin.controller;
//
//
//import com.dawateislami.centraldonation.admin.dto.BankDTO;
//import com.dawateislami.centraldonation.admin.dto.LegalEntityDTO;
//import com.dawateislami.centraldonation.admin.service.BankService;
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
//@RequestMapping(value = "/bank")
//public class BankController {
//
//    @Autowired
//    BankService service;
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
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
//    @Transactional
//    @GetMapping(value = "/addUpdate")
//    public ModelAndView getView(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes ) {
//
//        return this.service.getView(id, redirectAttributes);
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
//    @Transactional
//    @PostMapping(value = "/addUpdate")
//    public ModelAndView addUpdate(@Valid @ModelAttribute("dto") BankDTO bankDTO, BindingResult result, RedirectAttributes redirectAttributes) {
//
//        return this.service.addUpdate(bankDTO, result, redirectAttributes);
//    }
//}
