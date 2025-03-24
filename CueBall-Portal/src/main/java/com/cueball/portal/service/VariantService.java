package com.cueball.portal.service;


import com.cueball.portal.dto.VariantDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.Variant;
import com.cueballdb.repository.VariantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VariantService extends BaseService<Variant, VariantDTO, VariantRepository> {

    public VariantService(VariantRepository repository) {
        super(repository);
    }

    public ModelAndView findFindAllView(String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_VARIANT_VIEW_ALL);


        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<Variant> lists;
        Integer userId = this.getUserId();
        /** For Super Admin  */
        if (userId == 1) {
            lists = this.repository.findAllByFilters(search,enable,(pageNumber-1)*pageSize,pageSize,count);
        }
        /** For Non Super Admin  */
        else {
            lists = this.repository.findAllByFiltersAndDeleted(search,enable,(pageNumber-1)*pageSize,pageSize,count);
        }

        List<VariantDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_VARIANT_VIEW_ALL_DETAIL);
        }

        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_VARIANT_ADD_EDIT);
        if (id != null && id > 0) {
            VariantDTO variantDTO = this.findById(id);
            mav.addObject(Constants.RA_DTO, variantDTO);
        }
        else {
            VariantDTO variantDTO = new VariantDTO ();
            mav.addObject(Constants.RA_DTO, variantDTO);
        }
        return mav;
    }

    public ModelAndView addUpdate(VariantDTO variantDTO, BindingResult result, RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_VARIANT_ADD_EDIT);

        /**  Fetching Variants against the addition / updation of Variants if exists then throws an error **/
        Variant variant = repository.findByName(variantDTO.getName());
        if(variant != null && variantDTO.getId() != variant.getId()){
            result.rejectValue("name", "name.root", "Variant already exists!");
        }

        if (result.hasErrors()) {
            return mav;
        }

        this.save(variantDTO);
        String message = confirmBox(variantDTO);
        redirectAttributes.addFlashAttribute("message", message);
        mav = new ModelAndView("redirect:/variant/viewAll");
        return mav;
    }




    @Override
    public VariantDTO mapEntityToDto(Variant entity) {
        VariantDTO dto = new VariantDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setCreatedDate(entity.getCreatedDate().getTime());
        return dto;
    }

    @Override
    public Variant mapDtoToEntity(VariantDTO dto) {
        Variant entity = new Variant();
        BeanUtils.copyProperties(dto, entity);

        if (dto.getId() > 0) {
            entity.setModifyDate(new Date( System.currentTimeMillis()));
            entity.setCreatedDate(new Date(dto.getCreatedDate()));
        } else {
            entity.setCreatedDate(new Date ( System.currentTimeMillis()));
            entity.setModifyDate(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }
}
