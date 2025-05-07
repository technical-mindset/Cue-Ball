package com.cueball.portal.service;


import com.cueball.portal.dto.BookingDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.Booking;
import com.cueballdb.model.Room;
import com.cueballdb.model.RoomCategory;
import com.cueballdb.repository.BookingRepository;
import com.cueballdb.repository.RoomCategoryRepository;
import com.cueballdb.repository.RoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Log4j2
@Service
public class CustomerCheckOutService extends BaseService<Booking, BookingDTO, BookingRepository> {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomCategoryRepository categoryRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);


    public CustomerCheckOutService(BookingRepository repository) {
        super(repository);
    }

    @Override
    public BookingDTO mapEntityToDto(Booking entity) {
        BookingDTO dto = new BookingDTO();
        BeanUtils.copyProperties(entity, dto);

        //
        dto.setTimeIn(dateFormat.format(entity.getTimeIn()));
        dto.setTimeOut(dateFormat.format(entity.getTimeOut()));
        dto.setCheckIn(entity.getCheckIn() != null ? dateFormat.format(entity.getCheckIn()) : "N/A");
        dto.setCheckOut(entity.getCheckOut() != null ? dateFormat.format(entity.getCheckOut()) : "N/A");

        /** Room & Room - Category */
        dto.setRoomId(entity.getRoomId());
        dto.setRoomName(entity.getRoom().getName());
        dto.setCharges(entity.getRoom().getCharges());
        dto.setRoomCategoryId(entity.getRoom().getRoomCategory().getId());
        dto.setRoomCategoryName(entity.getRoom().getRoomCategory().getName());

        /** Calculation of total-time and total-charges */
        Date in = entity.getTimeIn();
        Date out = entity.getTimeOut();

        if (entity.getCheckIn() != null && entity.getCheckIn().getTime() > entity.getTimeIn().getTime()) {
            in = entity.getCheckIn();
        }
        if (entity.getCheckOut() != null && entity.getCheckOut().getTime() > entity.getTimeOut().getTime()) {
            out = entity.getCheckOut();
        }
        /** @@Charges conflict Pay Attention on this  */
//        dto.setTotalCharges(timeCalculation(in, out) * entity.getCharges()); /** Pay attention on this */
        /** -------------------------------------------------------------------------------------------- */
        dto.setTotalCharges(Math.ceil(timeCalculation(in, out) * entity.getRoom().getCharges()));
        dto.setTotalTime(String.valueOf(timeCalculation(in, out)));

        /** Customer details */
        dto.setCustomerId(entity.getCustomerId());
        dto.setCustomerName(entity.getCustomer().getName());
        dto.setContact(entity.getCustomer().getContact());
        dto.setEmail(entity.getCustomer().getEmail());


        dto.setCreatedAt(entity.getCreatedAt().getTime());
        dto.setModifiedAt(entity.getModifiedAt().getTime());
        return dto;
    }

    @Override
    public Booking mapDtoToEntity(BookingDTO dto) {
        return null;
    }



    public ModelAndView findFindAllView(String search ,Integer enable, Integer roomId, Integer roomCategoryId, String startDate, String endDate, Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_CUSTOMER_CHECKOUT_VIEW_ALL);

        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<Booking> lists = repository.findAllByFilterReport(search, enable, roomId, roomCategoryId, startDate, endDate, (pageNumber-1)*pageSize, pageSize, count);
        List<BookingDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        List<Room> rooms = this.roomRepository.findAllByEnableTrue();

        List<RoomCategory> roomCategories = this.categoryRepository.findAllByEnableTrue();

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_CUSTOMER_CHECKOUT_VIEW_ALL_DETAIL);
        }

        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject("rooms",rooms);
        mav.addObject("roomCategories",roomCategories);
        mav.addObject("roomFilter",true);
        mav.addObject("roomCategoryFilter",true);
        mav.addObject("toDateFilter",true);
        mav.addObject("fromDateFilter",true);
        mav.addObject("reportUrl", Constants.RA_BASE_URL + Constants.PORT);
        mav.addObject("reportBasePath", "/customer/report");
        mav.addObject(Constants.EXTRA_FILTERS,true);
        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }


    private double chargesCalculation(Double ... gameCharges){
        double result = 1.0;
        for (Double charge : gameCharges) {
            if (charge != null) {
                result *= charge;
            }
        }

        return result;

    }
}



