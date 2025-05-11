package com.cueball.portal.controller;


import com.cueball.portal.utils.Constants;
import com.cueballdb.model.InventoryCategory;
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
    public ModelAndView getById() {
        ModelAndView mav = new ModelAndView(Constants.RA_DASHBOARD);

        User user = this.getUser();

        Long gameCount;
        Long roomCount;
        Long inventoryCount;
//
//
//        if (user.getId() != 1){
            gameCount = gameRepository.count();
            roomCount = roomRepository.count();
            inventoryCount = inventoryRepository.count();
//        }
//        else {
//            transactionCount = transactionRepository.findTransactionCount();
//
//            campaignCount = campaignRepository.findCampaignCount();
//            operatingUnitCount = operatingUnitRepository.findOperatingUnitCount();
//            Integer legalEntityCount = legalEntityRepository.findLegalEntityCount();
//            mav.addObject ( "legalEntityCount", legalEntityCount);
//        }
//
//        Integer bankCount = bankRepository.findBankCount();
//        Integer currencyCount = currencyRepository.findCurrencyCount();

        mav.addObject ( "gameCount", gameCount);
        mav.addObject ( "roomCount", roomCount);
        mav.addObject ( "inventoryCount", inventoryCount);
//        mav.addObject ( "operatingUnitCount", operatingUnitCount);
//        mav.addObject ( "bankCount", bankCount);
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