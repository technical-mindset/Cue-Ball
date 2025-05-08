package com.cueball.portal.service;


import com.cueball.portal.dto.RestaurantDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.Restaurant;
import com.cueballdb.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService extends BaseService<Restaurant, RestaurantDTO, RestaurantRepository> {

    public RestaurantService(RestaurantRepository repository) {
        super(repository);
    }

    public ModelAndView findFindAllView(String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_RESTAURANT_VIEW_ALL);


        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<Restaurant> lists = this.repository.findAllByFilters(search,enable,(pageNumber-1)*pageSize,pageSize,count);
        List<RestaurantDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_RESTAURANT_VIEW_ALL_DETAIL);
        }

        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_RESTAURANT_ADD_EDIT);

        if (id != null && id > 0) {
            RestaurantDTO dto = this.findById(id);
            mav.addObject(Constants.RA_DTO, dto);
        }
        else {
            RestaurantDTO dto = new RestaurantDTO ();
            mav.addObject(Constants.RA_DTO, dto);
        }

        return mav;
    }

    public ModelAndView addUpdate(RestaurantDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_RESTAURANT_ADD_EDIT);

        /**  Fetching Restaurant against the addition / updation of Restaurant if exists then throws an error **/
        Restaurant restaurant = repository.findByTitle(dto.getTitle());
        if(restaurant != null && dto.getId() != restaurant.getId()){
            result.rejectValue("title", "title.root", "Restaurant title already exists!");
        }

        if (result.hasErrors()) {
            return mav;
        }

        this.save(dto);
        String message = confirmBox(dto);
        redirectAttributes.addFlashAttribute("message", message);
        mav = new ModelAndView("redirect:/restaurant/viewAll");
        return mav;
    }




    @Override
    public RestaurantDTO mapEntityToDto(Restaurant entity) {
        RestaurantDTO dto = new RestaurantDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setCreatedAt(entity.getCreatedAt().getTime());
        return dto;
    }

    @Override
    public Restaurant mapDtoToEntity(RestaurantDTO dto) {
        Restaurant entity = new Restaurant();
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
