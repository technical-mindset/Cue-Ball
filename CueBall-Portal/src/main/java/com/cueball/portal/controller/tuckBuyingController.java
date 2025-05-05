
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

}