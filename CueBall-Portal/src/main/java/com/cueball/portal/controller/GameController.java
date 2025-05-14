package com.cueball.portal.controller;


import com.cueball.portal.dto.GameDTO;
import com.cueball.portal.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/game")
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class GameController {

    @Autowired
    GameService service;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO create(@RequestBody GameDTO dto) {
        return this.service.save(dto); }



    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public GameDTO getById(@PathVariable("id")int id){
        return this.service.findById(id);
    }


    @Transactional
    @GetMapping(value = "/addUpdate")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ModelAndView getView(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes ) {
        return this.service.getView(id, redirectAttributes);
    }

    @Transactional
    @PostMapping(value = "/addUpdate")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ModelAndView addUpdate(@Valid @ModelAttribute("dto") GameDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {

        return this.service.addUpdate(dto, result, redirectAttributes);
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

