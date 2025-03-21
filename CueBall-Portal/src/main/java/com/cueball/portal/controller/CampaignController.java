//package com.dawateislami.centraldonation.admin.controller;
//
//
//import com.dawateislami.centraldonation.admin.dto.CampaignDTO;
//import com.dawateislami.centraldonation.admin.service.CampaignService;
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
//
//
//@RestController
//@RequestMapping(value = "/campaign")
//public class CampaignController {
//
//    @Autowired
//    CampaignService service;
//
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public CampaignDTO create(@RequestBody CampaignDTO dto) {
//        return service.save(dto); }
//
//
////    @RequestMapping(value = "/all", method = RequestMethod.GET)
////    public GenericListResponse findTags() {
////        return service.findAllEnable();
////    }
//
//
//
//    @RequestMapping(value="/{id}", method = RequestMethod.GET)
//    public CampaignDTO getById(@PathVariable("id")int id){
//        return service.findById(id);
//    }
//
//
//    @Transactional
//    @GetMapping(value = "/addUpdate")
//    public ModelAndView getView(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes ) {
//
//        return service.getView(id, redirectAttributes);
//    }
//
//    @Transactional
//    @PostMapping(value = "/addUpdate")
//    public ModelAndView addUpdate(@ModelAttribute("dto") @Valid CampaignDTO campaignDTO, BindingResult result, RedirectAttributes redirectAttributes) {
//        return service.addUpdate(campaignDTO, result, redirectAttributes);
//
//    }
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
//
//}
//
