package com.cueball.portal.service;


import com.cueball.portal.dto.BookingDTO;
import com.cueball.portal.dto.TaskDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.*;
import com.cueballdb.repository.BookingRepository;
import com.cueballdb.repository.CustomerRepository;
import com.cueballdb.repository.RoomRepository;
import com.cueballdb.repository.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Log4j2
@Service
public class TaskService extends BaseService<Task, TaskDTO, TaskRepository> {

    public TaskService(TaskRepository repository) {
        super(repository);
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    @Override
    public TaskDTO mapEntityToDto(Task entity) {
        TaskDTO dto = new TaskDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setTaskDate(entity.getTaskDate() != null ? this.dateFormat.format(entity.getTaskDate()) : "N/A");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
        dto.setLastAlertSent(entity.getLastAlertSent() != null ? entity.getLastAlertSent().format(formatter) : "N/A");

        /** @CASE:(User-Empty) :- User Entity would found empty while saving the data because No mapping establish with User Table
        //        dto.setUserName(entity.getUser().getUsername());
        //        dto.setUserName(entity.getUser().getRoles().get(0).getName());
         * @SOLUTION:- Just put N/A or null for saving the data because I established the JOIN between task and User in Custom Query so there
         * this could be handled always */
        dto.setUserName(entity.getUser() != null ? entity.getUser().getUsername() : "N/A");
        dto.setRoleName(entity.getUser() != null ? entity.getUser().getRoles().get(0).getName() : "N/A");


        dto.setCreatedAt(entity.getCreatedAt().getTime());
        dto.setModifiedAt(entity.getModifiedAt().getTime());
        return dto;
    }

    @Override
    public Task mapDtoToEntity(TaskDTO dto) {
        Task entity = new Task();

        BeanUtils.copyProperties(dto, entity);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        log.info("Task Service {}", "Mapping DTO to Entity");

        User user = this.userRepository.findById(dto.getUserId()).get();
        entity.setShift(user.getShift());

        Date taskDate = null;

        try {
            taskDate = this.dateFormat.parse(dto.getTaskDate());
            entity.setTaskDate(taskDate);
            log.info("Task Service {}", "Conversion of Task Date is now completed !!");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("Task Service {}", "After Catch Block !!");

        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
            entity.setCreatedAt(new Date (dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date ( System.currentTimeMillis()));
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }



    public ModelAndView findFindAllView(String search ,Integer enable, Integer complete, Integer userId, String startDate, String shift, Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_TASK_VIEW_ALL);

        mav.addObject ( "userid", this.getUserId());

        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long [] count = {0};
        List<Task> lists = null;

        List<String> adminRoles = List.of("ROLE_SUPER_ADMIN", "ROLE_ADMIN");

        List<User> users = null;

        if (this.getUser().getRoles().stream().anyMatch(role -> adminRoles.contains(role.getName()))) {
            users = this.userRepository.findAllUsersForAdmin();
        }
        else {
            users = this.userRepository.findAllUsersByRoles(this.getUser().getShift());
            /** Adding manual Manager as user for staff filter drop-down selection */
            users.add(this.getUser());
        }

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_TASK_VIEW_ALL_DETAIL);
        }

        List<String> targetRoles = List.of("ROLE_TOILET_CLEANER", "ROLE_CLEANER", "ROLE_USER");

        if (this.getUser().getRoles().stream().anyMatch(role -> targetRoles.contains(role.getName()))) {
            System.out.println("::::::::::::::: In User Section :::::::::::::::::::::");
            lists = repository.findAllByFilters(search, enable, complete, this.getUserId(), "NaN", "NaN",  true,(pageNumber-1)*pageSize, pageSize, count);
        }
        else if (this.getUser().getRoles().stream().anyMatch(role -> "ROLE_MANAGER".contains(role.getName()))) {

            System.out.println("::::::::::::::: In Manager Section :::::::::::::::::::::");
//            int USER_ID = userId != null ? (userId != 0 ? userId : this.getUserId()) : this.getUserId();
            lists = repository.findAllByFilters(search, enable, complete, userId, "NaN", this.getUser().getShift(),  true, (pageNumber-1)*pageSize, pageSize, count);

            mav.addObject("users", users);
            mav.addObject("userFilter",true);
            mav.addObject(Constants.EXTRA_FILTERS,true);
        }
        else {
            System.out.println("::::::::::::::: In Admin Section :::::::::::::::::::::");
            lists = repository.findAllByFilters(search, enable, complete, userId, startDate, shift, false, (pageNumber-1)*pageSize, pageSize, count);
            mav.addObject("users", users);
            mav.addObject("shifts", Constants.shifts);
            mav.addObject("fromDateFilter",true);
            mav.addObject("userFilter",true);
            mav.addObject("shiftFilter",true);
            mav.addObject(Constants.EXTRA_FILTERS,true);
        }

        List<TaskDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        mav.addObject("UserId" , this.getUserId());
        mav.addObject("taskFilter",true);
        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_TASK_ADD_EDIT);

        List<String> adminRoles = List.of("ROLE_SUPER_ADMIN", "ROLE_ADMIN");
        List<User> userList = null;
        if (this.getUser().getRoles().stream().anyMatch(role -> adminRoles.contains(role.getName()))) {
            userList = this.userRepository.findAllUsersForAdmin();
        }
        else {
            userList = this.userRepository.findAllUsersByRoles(this.getUser().getShift());
        }


        mav.addObject("userList", userList);
        mav.addObject("shifts",Constants.shifts);


        if (id != null && id > 0) {
            TaskDTO dto = this.findById(id);
            mav.addObject(Constants.RA_DTO, dto);
        }
        else {
            TaskDTO dto = new TaskDTO();
            mav.addObject(Constants.RA_DTO, dto);
        }

        return mav;
    }

    public ModelAndView addUpdate(TaskDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_TASK_ADD_EDIT);

        List<String> adminRoles = List.of("ROLE_SUPER_ADMIN", "ROLE_ADMIN");
        List<User> userList = null;
        if (this.getUser().getRoles().stream().anyMatch(role -> adminRoles.contains(role.getName()))) {
            userList = this.userRepository.findAllUsersForAdmin();
        }
        else {
            userList = this.userRepository.findAllUsersByRoles(this.getUser().getShift());
        }


        mav.addObject("userList", userList);
        mav.addObject("shifts",Constants.shifts);


        if (result.hasErrors()) {
            result.reject("valid.remove.errors");
            return mav;
        }

        this.save(dto);
        String message = confirmBox(dto);
        redirectAttributes.addFlashAttribute("message", message);

        mav = new ModelAndView("redirect:/task/viewAll");
        return mav;
    }

    /** SENDING NOTIFICATION */
    public Map<String, Object> sendNotification(int id , boolean complete) {
        int rowsAffected = this.repository.updateTaskStatus(id, complete);
        Map<String, Object> response = new HashMap<>();

        /** passing the error cade for handling the error & success in ajax success case */
        String message;
        Integer successCode = 0;
        if (rowsAffected > 0) {
            message = Constants.RA_SWEET_ALERT_SUCCESS;
            successCode = 1;
        } else {
            message = Constants.RA_SWEET_ALERT_FAILED;
        }

        response.put("message", message);
        response.put("success", successCode);

        return response;
    }

}



