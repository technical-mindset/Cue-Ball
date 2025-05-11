package com.cueball.portal.controller;


import com.cueball.portal.utils.Constants;
import javax.servlet.http.Cookie;
import com.cueballdb.model.User;
import com.cueballdb.repository.GameRepository;
import com.cueballdb.repository.InventoryRepository;
import com.cueballdb.repository.RoomRepository;
import com.cueballdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/dashboard")
public class Dashboard {

//        @Autowired
//        TransactionRepository transactionRepository;
//        @Autowired
//        BankRepository bankRepository;
//        @Autowired
//        CurrencyRepository currencyRepository;
//        @Autowired
//        CampaignRepository campaignRepository;
//        @Autowired
//        OperatingUnitRepository operatingUnitRepository;
//        @Autowired
//        LegalEntityRepository legalEntityRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private GameRepository gameRepository;

        @Autowired
UserRepository userRepository;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getById(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(Constants.RA_DASHBOARD);

        User user = this.getUser();


        // Check if cookie already exists
        Cookie existingCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("cbusr".equals(cookie.getName())) {
                    existingCookie = cookie;
                    break;
                }
            }
        }

        // Set cookie only if it doesn't exist
        if (existingCookie == null) {
            Cookie cookie = new Cookie("cbusr", String.valueOf(user.getId()));
            cookie.setSecure(false); // Only sent over HTTPS
            cookie.setHttpOnly(false);
            cookie.setPath("/"); // Accessible across entire app
            cookie.setMaxAge(7 * 24 * 60 * 60); // Cookie expiry: 7 days (in seconds)
            response.addCookie(cookie);
            System.out.println(":::::::::: Cookie set successfully! :::::::::::::");
            System.out.println(":::::::::: Cookie set successfully! :::::::::::::");
            System.out.println(":::::::::: Cookie set successfully! :::::::::::::");
        } else {
            System.out.println(":::::::::: Cookie already exists! :::::::::::::");
            System.out.println(":::::::::: Cookie already exists! :::::::::::::");
            System.out.println(":::::::::: Cookie already exists! :::::::::::::");
            System.out.println(":::::::::: Cookie already exists! :::::::::::::");
        }


        Long gameCount;
        Long roomCount;
        Long inventoryCount;

            gameCount = gameRepository.count();
            roomCount = roomRepository.count();
            inventoryCount = inventoryRepository.count();


        mav.addObject ( "gameCount", gameCount);
        mav.addObject ( "roomCount", roomCount);
        mav.addObject ( "inventoryCount", inventoryCount);
        mav.addObject ( "userName", user.getFullname());
        mav.addObject ( "userid", user.getId());
        return mav;
    }

    /**  Retrieving the Operating Units for logged-in user  */
    public User getUser(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        return user;
    }
        }