package com.cueball.portal.service;


import com.cueball.portal.dto.BookingDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.*;
import com.cueballdb.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Log4j2
@Service
public class BookingService extends BaseService<Booking, BookingDTO, BookingRepository> {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);


    public BookingService(BookingRepository repository) {
        super(repository);
    }

    @Override
    public BookingDTO mapEntityToDto(Booking entity) {
        BookingDTO dto = new BookingDTO();
        BeanUtils.copyProperties(entity, dto);


        dto.setTimeIn(dateFormat.format(entity.getTimeIn()));
        dto.setTimeOut(dateFormat.format(entity.getTimeOut()));

        dto.setRoomId(entity.getRoomId());
        dto.setCustomerId(entity.getCustomerId());

        Customer customer = this.customerRepository.findById((dto.getCustomerId())).get();
        dto.setCustomerName(customer.getName());
        dto.setContact(customer.getContact());
        dto.setEmail(customer.getEmail());

        Room room = this.roomRepository.findById((entity.getRoomId())).get();
        dto.setRoomName(room.getName());

        dto.setCreatedAt(entity.getCreatedAt().getTime());
        dto.setModifiedAt(entity.getModifiedAt().getTime());
        return dto;
    }

    @Override
    public Booking mapDtoToEntity(BookingDTO dto) {
        Booking entity = new Booking();

        BeanUtils.copyProperties(dto, entity);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);

        Date timeIn = null;
        Date timeOut = null;

        try {
            timeIn = dateFormat.parse(dto.getTimeIn());
            timeOut = dateFormat.parse(dto.getTimeOut());

            entity.setTimeIn(timeIn);
            entity.setTimeOut(timeOut);

            /**
             * @(RFID) these fields persist from RFID
             * */
            if (dto.getCheckIn() != null && dto.getCheckOut() != null) {
                entity.setCheckIn(dateFormat.parse(dto.getCheckIn()));
                entity.setCheckOut(dateFormat.parse(dto.getCheckOut()));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /** Creating Customer's Object */

        Customer customer = new Customer();
        customer.setName(dto.getCustomerName());
        customer.setEmail(dto.getEmail() != null ? !dto.getEmail().isEmpty() ? dto.getEmail() : "N/A" : "N/A");
        customer.setContact(dto.getContact());
        log.info("Booking Service {}", "Creating Customer's Bean");
        customer.setCreatedAt(new Date(System.currentTimeMillis()));
        customer.setCreatedBy(this.getUserId());
        customer.setModifiedAt(new Date(System.currentTimeMillis()));
        customer.setModifyBy(this.getUserId());

        customer = this.customerRepository.save(customer);
        log.info("Booking Service {}", "Persisting Customer's Bean");

        entity.setCustomerId((customer.getId()));

        log.info("Booking Service {}", "Calculation of charges");
        double charges = this.roomRepository.findById(((dto.getRoomId()))).get().getCharges();

        /** handling the charges condition of update case after check-out */
        if (entity.getCheckOut() != null) {
            charges = charges * timeCalculation(entity.getTimeIn(), entity.getCheckOut());
        }
        else {
            charges = charges * timeCalculation(entity.getTimeIn(), entity.getTimeOut());
        }
        entity.setCharges(charges);

        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
            entity.setCreatedAt(new Date (dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date ( System.currentTimeMillis()));
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }



    public ModelAndView findFindAllView(String search ,Integer enable, Integer roomId, Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_BOOKING_VIEW_ALL);

        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<Booking> lists = repository.findAllByFilters(search, enable, roomId, (pageNumber-1)*pageSize, pageSize, count);
//        List<Booking> lists = repository.findAll();
        List<BookingDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        List<Room> rooms = this.roomRepository.findAllByEnableTrue();

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_BOOKING_VIEW_ALL_DETAIL);
        }

        mav.addObject("rooms",rooms);
        mav.addObject("roomFilter",true);
        mav.addObject(Constants.EXTRA_FILTERS,true);
        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        mav.addObject("route", Constants.RA_BASE_URL + Constants.PORT + "/tuckShop/menu?bkId=");
        return mav;
    }

    public ModelAndView getView(Integer id, Integer roomId, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_BOOKING_ADD_EDIT);

        List<Room> roomList = this.roomRepository.findAllByEnableTrue();

        mav.addObject("roomList", roomList);


        BookingDTO bookingDTO;
        if (id != null && id > 0) {
            bookingDTO = this.findById(id);
            mav.addObject(Constants.RA_DTO, bookingDTO);
        }
        else {
            bookingDTO = new BookingDTO();
            mav.addObject(Constants.RA_DTO, bookingDTO);
        }

        if (roomId != null && roomId > 0) {
            bookingDTO.setRoomId(roomId);
        }

        return mav;
    }

    public ModelAndView addUpdate(BookingDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_BOOKING_ADD_EDIT);

        /** Condition for precation of case date-time null or empty before returning the errors with VIEW  */
        if (dto.getTimeIn().length() > 0 && dto.getTimeOut().length() > 0) {

             try {
                Date timeIn = dateFormat.parse(dto.getTimeIn());
                Date timeOut = dateFormat.parse(dto.getTimeOut());

                /**  Handling the conflicts of date-time for another entry */
                if (timeOut.getTime() < System.currentTimeMillis()) {
                    result.rejectValue("timeOut", "error.timeOut", "Time Out must be greater than current date-time!");
                }
                else if (timeIn != null && timeOut != null) {
                    // Fetching the
                    List<Booking> conflictBookings = this.repository.findConflictBookings(dto.getId(), timeIn, timeOut, dto.getRoomId());
                    log.info("conflict Booking List Length: {}" + conflictBookings.size());

                    if (conflictBookings.size() > 0) {
                        result.rejectValue("timeIn", "error.timeIn", "Select another time slot!");
                        result.rejectValue("timeOut", "error.timeOut", "Select another time slot!");
                    }
                }

            }
             catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }



        List<Room> roomList = this.roomRepository.findAllByEnableTrue();

        mav.addObject("roomList", roomList);


        if (result.hasErrors()) {
            result.reject("valid.remove.errors");
            return mav;
        }

        this.save(dto);
        String message = confirmBox(dto);
        redirectAttributes.addFlashAttribute("message", message);

        mav = new ModelAndView("redirect:/booking/viewAll");
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



