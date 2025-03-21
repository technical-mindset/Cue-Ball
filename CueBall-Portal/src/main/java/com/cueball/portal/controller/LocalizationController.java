//package com.dawateislami.centraldonation.admin.controller;
//
//import com.dawateislami.centraldonation.admin.dto.LocalizationDTO;
//import com.dawateislami.centraldonation.admin.response.GenericListResponse;
//import com.dawateislami.centraldonation.admin.service.LocalizationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping(value="/localization")
//public class LocalizationController {
//
//    @Autowired
//    LocalizationService service;
//
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public LocalizationDTO create(@RequestBody LocalizationDTO dto) {
//        return service.save(dto);
//    }
//
//
//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public GenericListResponse findAchievement() {
//        return service.findAllEnable();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public LocalizationDTO getById( @PathVariable("id") int id) {
//        return service.findById(id);
//    }
//
//
//    @Transactional
//    @GetMapping(value = "/addUpdate")
//    public ModelAndView getView(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes) {
//        return service.getView(id, redirectAttributes);
//    }
//
//    @Transactional
//    @PostMapping(value = "/addUpdate")
//    public ModelAndView addUpdate(@ModelAttribute("dto") @Valid LocalizationDTO localizationDTO, BindingResult result, RedirectAttributes redirectAttributes) {
//        return service.addUpdate( localizationDTO, result, redirectAttributes);
//    }
//
//
////    @GetMapping(value = "/addUpdate/viewAll")
////    public ModelAndView findAllViewInEditCase(
////            @RequestParam(value = "ps", required = false, defaultValue = "5") Integer pageSize,
////            @RequestParam(value = "pn", required = false, defaultValue = "1") Integer pageNumber,
////            @RequestParam(value = "ajax", required = false, defaultValue = "false") boolean ajax,
////            @RequestParam(value = "langCode",required = false )String langCode) {
////        return service.findFindAllView(pageSize, pageNumber, ajax,langCode);
////    }
//
//
//    @GetMapping(value = "/viewAll")
//    public ModelAndView findAllView(
//            @RequestParam(value = "lang",required = false )String lang,
//            @RequestParam(value = "search",required = false )String search,
//            @RequestParam(value = "enable",required = false )Integer enable,
//            @RequestParam(value = "ps", required = false, defaultValue = "10") Integer pageSize,
//            @RequestParam(value = "pn", required = false, defaultValue = "1") Integer pageNumber,
//            @RequestParam(value = "ajax", required = false, defaultValue = "false") boolean ajax
//    ) {
////        return service.findFindAllView(pageSize, pageNumber, ajax,langCode);
//        return service.findFindAllView(lang,search,enable,pageSize,pageNumber,ajax);
//    }
//
//
//}
