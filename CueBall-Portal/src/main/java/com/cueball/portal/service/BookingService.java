//package com.cueball.portal.service;
//
//
//import com.cueball.portal.dto.BookingDTO;
//import com.cueball.portal.dto.RoomDTO;
//import com.cueball.portal.utils.Constants;
//import com.cueballdb.model.*;
//import com.cueballdb.repository.*;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Log4j2
//@Service
//public class BookingService extends BaseService<Booking, BookingDTO, BookingRepository> {
//
//    @Autowired
//    private RoomRepository roomRepository;
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    public BookingService(BookingRepository repository) {
//        super(repository);
//    }
//
//    @Override
//    public BookingDTO mapEntityToDto(Booking entity) {
//        BookingDTO dto = new BookingDTO();
//        BeanUtils.copyProperties(entity, dto);
//
//
//        dto.setRoomCategoryId(entity.getRoomCategory().getId());
//
//        List<Integer> games = Arrays.asList(entity.getGames().split(","))
//                .stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
//
//        dto.setGameIds(games);
//        dto.setRoomCategoryName(entity.getRoomCategory().getName());
//        dto.setCreatedDate(entity.getCreatedDate().getTime());
//        dto.setModifyDate(entity.getModifyDate().getTime());
//        return dto;
//    }
//
//    @Override
//    public Booking mapDtoToEntity(BookingDTO dto) {
//        Booking entity = new Booking();
//
//        BeanUtils.copyProperties(dto, entity);
//
//        Customer customer = new Customer();
//        customer.setName(dto.getCustomerName());
//        customer.setEmail(dto.getEmail());
//        customer.setContact(dto.getContact());
//
//        customer = this.customerRepository.save(customer);
//
//        entity.setCustomerId(String.valueOf(customer.getId()));
//
//        double charges = this.roomRepository.findById((Integer.parseInt(dto.getRoomId()))).get().getCharges();
//
//
//        entity.setCharges(totalCharges);
//
//        if (dto.getId() > 0) {
//            entity.setModifyDate(new Date ( System.currentTimeMillis()));
//            entity.setCreatedDate(new Date (dto.getCreatedDate()));
//        } else {
//            entity.setCreatedDate(new Date ( System.currentTimeMillis()));
//            entity.setModifyDate(new Date ( System.currentTimeMillis()));
//        }
//        return entity;
//    }
//
//
//
//    public ModelAndView findFindAllView(String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_VIEW_ALL);
//
//        if(pageSize == null || pageSize <= 0 ) {
//            pageSize = Constants.MAX_PER_PAGE;
//        }
//        if(pageNumber == null || pageNumber <= 0) {
//            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
//        }
//
//        long []count = {0};
//        List<Room> lists = repository.findAllByFilters(search, enable,(pageNumber-1)*pageSize, pageSize, count);
//        List<RoomDTO> DTOs = lists
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//
//        if (ajax) {
//            mav = new ModelAndView(Constants.RA_PAGE_ROOM_VIEW_ALL_DETAIL);
//        }
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
//        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
//        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
//        mav.addObject("totalCount", count[0]);
//        mav.addObject(Constants.RA_LIST, DTOs);
//        return mav;
//    }
//
//    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){
//
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_ADD_EDIT);
//
//        List<RoomCategory> roomCategoryList = this.roomCategoryRepository.findAllByEnableTrue();
//        List<Game> gameList = this.gameRepository.findAllByEnableTrue();
//
//
//        mav.addObject("games", gameList);
//        mav.addObject("roomCategoryList", roomCategoryList);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        //data access permission w.r.t. userLang
//        String userLang= "ur";
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//
//        if (id != null && id > 0) {
//            RoomDTO roomDTO = this.findById(id);
//            mav.addObject(Constants.RA_DTO, roomDTO);
//        }
//        else {
//            RoomDTO roomDTO = new RoomDTO();
//            mav.addObject(Constants.RA_DTO, roomDTO);
//        }
//
//        return mav;
//    }
//
//    public ModelAndView addUpdate(RoomDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_ADD_EDIT);
//
//        List<RoomCategory> roomCategoryList = this.roomCategoryRepository.findAllByEnableTrue();
//        List<Game> gameList = this.gameRepository.findAllByEnableTrue();
//
//        mav.addObject("games", gameList);
//        mav.addObject("roomCategoryList", roomCategoryList);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        //data access permission w.r.t. userLang
//        String userLang= "ur";
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//
//        if (result.hasErrors()) {
//            result.reject("valid.remove.errors");
//            return mav;
//        }
//
//        this.save(dto);
//        String message = confirmBox(dto);
//        redirectAttributes.addFlashAttribute("message", message);
//
//        mav = new ModelAndView("redirect:/room/viewAll");
//        return mav;
//    }
//
//
//    private double chargesCalculation(Double ... gameCharges){
//        double result = 1.0;
//        for (Double charge : gameCharges) {
//            if (charge != null) {
//                result *= charge;
//            }
//        }
//
//        return result;
//
//    }
//
//}
//
//
//
