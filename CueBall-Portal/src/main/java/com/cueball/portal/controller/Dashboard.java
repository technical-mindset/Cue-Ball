package com.cueball.portal.controller;


import com.cueball.portal.utils.Constants;
import javax.servlet.http.Cookie;
import com.cueballdb.model.User;
import com.cueballdb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping(value = "/dashboard")
public class Dashboard {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;


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


        Long gameCount = gameRepository.count();
        Long roomCount = roomRepository.count();
        Long inventoryCount = inventoryRepository.count();
        Long availableRoomCount = roomRepository.findAvailableRoomsCount();
        Long taskShiftCount = 0L;
        Long taskCompletedShiftCount = 0L;

        if (this.getUser().getRoles().stream().anyMatch(x -> List.of("ROLE_SUPER_ADMIN", "ROLE_ADMIN").contains(x.getName()))) {
            taskShiftCount = taskRepository.countTasksByShiftTodayForAdmin(null);
            taskCompletedShiftCount = taskRepository.countTasksByShiftTodayForAdmin(true);
        }
        else if (this.getUser().getRoles().stream().anyMatch(x -> "ROLE_MANAGER".contains(x.getName()))) {
            taskShiftCount = taskRepository.countTasksByShiftToday(this.getUser().getShift(), null, null);
            taskCompletedShiftCount = taskRepository.countTasksByShiftToday(this.getUser().getShift(), true, null);
        }
        else {
            taskShiftCount = taskRepository.countTasksByShiftToday(this.getUser().getShift(), null, this.getUser().getId());
            taskCompletedShiftCount = taskRepository.countTasksByShiftToday(this.getUser().getShift(), true, this.getUser().getId());
        }


        mav.addObject("gameCount", gameCount);
        mav.addObject("roomCount", roomCount);
        mav.addObject("availableRoomCount", availableRoomCount);
        mav.addObject("inventoryCount", inventoryCount);
        mav.addObject("taskShiftCount", taskShiftCount);
        mav.addObject("taskCompletedShiftCount", taskCompletedShiftCount);
        mav.addObject("userName", user.getFullname());
        mav.addObject("userid", user.getId());
        return mav;
    }

    /**  Retrieving the Operating Units for logged-in user  */
    public User getUser(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        return user;
    }
        }