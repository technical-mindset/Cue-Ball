package com.cueball.portal.service;


import com.cueball.portal.dto.RoomCategoryDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.RoomCategory;
import com.cueballdb.repository.RoomCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomCategoryService extends BaseService<RoomCategory, RoomCategoryDTO, RoomCategoryRepository> {

    public RoomCategoryService(RoomCategoryRepository repository) {
        super(repository);
    }

    public ModelAndView findFindAllView(String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_CATEGORY_VIEW_ALL);


        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<RoomCategory> lists = this.repository.findAllByFilters(search,enable,(pageNumber-1)*pageSize,pageSize,count);
        List<RoomCategoryDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_ROOM_CATEGORY_VIEW_ALL_DETAIL);
        }

        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_CATEGORY_ADD_EDIT);
        if (id != null && id > 0) {
            RoomCategoryDTO roomCategoryDTO = this.findById(id);
            mav.addObject(Constants.RA_DTO, roomCategoryDTO);
        }
        else {
            RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO ();
            mav.addObject(Constants.RA_DTO, roomCategoryDTO);
        }
        return mav;
    }

    public ModelAndView addUpdate(RoomCategoryDTO roomCategoryDTO, BindingResult result, RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_CATEGORY_ADD_EDIT);

        /**  Fetching Room Categories against the addition / updation of room's category if exists then throws an error **/
        RoomCategory category = repository.findByName(roomCategoryDTO.getName());
        if(category != null && roomCategoryDTO.getId() != category.getId()){
            result.rejectValue("name", "name.root", "Category code already exists!");
        }

        if (result.hasErrors()) {
            return mav;
        }

        this.save(roomCategoryDTO);
        String message = confirmBox(roomCategoryDTO);
        redirectAttributes.addFlashAttribute("message", message);
        mav = new ModelAndView("redirect:/roomCategory/viewAll");
        return mav;
    }

    public ModelAndView allCategories() {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_CATEGORY_ALL);

        List<RoomCategory> lists = this.repository.findAllByEnableTrueAndDeleteFalse();
        List<RoomCategoryDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        mav.addObject(Constants.RA_LIST, DTOs);
        mav.addObject("route", Constants.RA_BASE_URL + Constants.PORT + "/rooms/all?cat=");
        return mav;
    }


    @Override
    public RoomCategoryDTO mapEntityToDto(RoomCategory entity) {
        RoomCategoryDTO dto = new RoomCategoryDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setCreatedAt(entity.getCreatedAt().getTime());
        return dto;
    }

    @Override
    public RoomCategory mapDtoToEntity(RoomCategoryDTO dto) {
        RoomCategory entity = new RoomCategory();
        BeanUtils.copyProperties(dto, entity);

        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date( System.currentTimeMillis()));
            entity.setCreatedAt(new Date(dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date ( System.currentTimeMillis()));
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }
}
