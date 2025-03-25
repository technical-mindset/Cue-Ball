package com.cueball.portal.service;


import com.cueball.portal.dto.UserDTO;
import com.cueball.portal.response.GenericListResponse;
import com.cueball.portal.utils.Constants;
//import com.dawateislami.centraldonationdb.model.OperatingUnit;
import com.cueballdb.model.Role;
import com.cueballdb.model.User;
//import com.dawateislami.centraldonationdb.repository.OperatingUnitRepository;
import com.cueballdb.repository.RoleRepository;
import com.cueballdb.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserService extends BaseService<User, UserDTO, UserRepository>{


    @Autowired
    public RoleRepository roleRepository;

    public UserService ( UserRepository repository) {
        super(repository);
    }

    @Override
    public UserDTO mapEntityToDto(User entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);

        //Getting Role id for selection in View
        List<Integer> roleIds =  entity.getRoles().stream()
                .map(Role ::getId)
                .collect(Collectors.toList());


        dto.setRoleId(roleIds);
        dto.setCreatedAt(entity.getCreatedAt().getTime());
        dto.setModifiedAt( entity.getModifiedAt().getTime () );
        return dto;
    }

    @Override
    public User mapDtoToEntity(UserDTO dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);

        //Setting Roles
        List<Role> roles = roleRepository.findByRoleList(dto.getRoleId());
        entity.setRoles(roles);

        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date(System.currentTimeMillis()));
            entity.setCreatedAt(new Date(dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date(System.currentTimeMillis()));
            entity.setModifiedAt(new Date(System.currentTimeMillis()));
        }
        return entity;

    }

    public GenericListResponse findAll(Integer pageNumber, Integer pageSize, boolean page) {

        List<UserDTO> enTypeList = null;
        if (page) {
            Page<User> enTypes = repository.findAll(pageable(pageNumber, pageSize));
            enTypeList = enTypes
                    .stream()
                    .map(this::mapEntityToDto)
                    .collect(Collectors.toList());

            return GenericListResponse.success(enTypeList, enTypes.getTotalPages());
        } else {
            enTypeList = repository.findAll()
                    .stream()
                    .map(this::mapEntityToDto)
                    .collect(Collectors.toList());
            return GenericListResponse.success(enTypeList, enTypeList.size());
        }

    }


    public ModelAndView findFindAllView(Integer pageSize, Integer pageNumber, boolean ajax) {

        ModelAndView mav = new ModelAndView( Constants.RA_PAGE_USER_VIEW_ALL);

        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_USER_VIEW_ALL_DETAIL);
        }

        Page<User> lists;

        /**  getting users lists when user is not big_admin     */
        if(getUserId()!=1){
            lists = repository.findUsersById(pageable(pageNumber, pageSize));
        }
        else{
             lists = repository.findAll(pageable(pageNumber, pageSize));
        }

        List<UserDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, lists.getTotalPages());
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;

    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_USER_ADD_EDIT);

        /** getting roles when user is not big_admin   */
        if(getUserId()!=1){
            mav.addObject(Constants.RA_ROLE, roleRepository.findRolesByName());
        }
        else{
            mav.addObject(Constants.RA_ROLE, roleRepository.findAll());
        }

        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        if(id != null && id > 0){
            UserDTO userDTO =this.findById(id);
            userDTO.setPassword("Dummy");
            //data access permission w.r.t. users
            if(userDTO.getId()!=1 || auth.getName().equals("big_admin")){
                mav.addObject(Constants.RA_DTO,userDTO );
            }
            else{
                mav = new ModelAndView("redirect:/users/viewAll");
                redirectAttributes.addFlashAttribute("error",true);
                redirectAttributes.addFlashAttribute("message", "Access Denied");
            }
        }
        else {
            UserDTO userDTO = new UserDTO();
            mav.addObject(Constants.RA_DTO, userDTO);
        }
        return mav;
    }

    public ModelAndView addUpdate(UserDTO dto, BindingResult result , RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_USER_ADD_EDIT);

        /**  for checking duplicate value  */
        List<User> username=repository.findUsersByUsername (dto.getId(),dto.getUsername());
        boolean checkUsername=username.isEmpty();

        if(checkUsername==false){
            result.rejectValue("username", "error.username", Constants.RA_EDIT_MESSAGE);
        }

        /**  getting roles when user is not big_admin  */
        if(getUserId()!=1){
            mav.addObject(Constants.RA_ROLE, roleRepository.findRolesByName());
        }
        else{
            mav.addObject(Constants.RA_ROLE, roleRepository.findAll());
        }

        if (result.hasErrors()) {
            result.reject("valid.remove.errors");
            return mav;
        }


        if(dto.getPassword().equals("Dummy")){
            UserDTO userDTO =this.findById(dto.getId());
            dto.setPassword(userDTO.getPassword());
        }
        else{
            dto.setPassword(passwordEncoder(dto.getPassword()));
        }

        this.save ( dto );
        String message = confirmBox ( dto );
        redirectAttributes.addFlashAttribute ( "message", message );

        mav = new ModelAndView("redirect:/users/viewAll");
        return mav;

    }

    public GenericListResponse findAllEnable() {

        List<UserDTO> dto = null;

        dto = repository.findAllEnable()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return GenericListResponse.success(dto, dto.size());

    }

    public String passwordEncoder(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

}
