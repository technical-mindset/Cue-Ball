package com.cueball.portal.service;


import com.cueball.portal.dto.InventoryDTO;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class InventoryService extends BaseService<Inventory, InventoryDTO, InventoryRepository> {

    @Autowired
    private InventoryCategoryRepository categoryRepository;

    @Autowired
    private VariantRepository variantRepository;

    public InventoryService(InventoryRepository repository) {
        super(repository);
    }

    @Override
    public InventoryDTO mapEntityToDto(Inventory entity) {
        InventoryDTO dto = new InventoryDTO();
        BeanUtils.copyProperties(entity, dto);

        /** Due to Mapping, Inventory-Category Id set into dto from entity's Inventory-Category's object  */
        dto.setInventoryCategoryId(entity.getInventoryCategory().getId());
        dto.setInventoryCategoryName(entity.getInventoryCategory().getName());

        /** Retrieving Variant's name  */
        Variant variant = this.variantRepository.findById(dto.getVariantId()).get();
        dto.setVariantName(variant.getName());


        dto.setCreatedAt(entity.getCreatedAt().getTime());
        dto.setModifiedAt(entity.getModifiedAt().getTime());
        return dto;
    }

    @Override
    public Inventory mapDtoToEntity(InventoryDTO dto) {
        Inventory entity = new Inventory();

        BeanUtils.copyProperties(dto, entity);

        InventoryCategory inventoryCategory = this.categoryRepository.findById(dto.getInventoryCategoryId()).get();
        entity.setInventoryCategory(inventoryCategory);


        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
            entity.setCreatedAt(new Date (dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date ( System.currentTimeMillis()));
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }


    public ModelAndView findFindAllView(String search ,Integer enable, Integer variantId, Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_INVENTORY_VIEW_ALL);

        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<Inventory> lists = repository.findAllByFilters(search, enable, variantId, (pageNumber-1)*pageSize, pageSize, count);
        List<InventoryDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        List<Variant> variants = this.variantRepository.findAllByEnableTrue();


        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_INVENTORY_VIEW_ALL_DETAIL);
        }

        /**  Passing Obj and ObjectName for generic filters population while searching  */
        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject("variants",variants);
        mav.addObject("variantFilter",true);
        mav.addObject(Constants.EXTRA_FILTERS,true);
        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);

        /** Generic things must be on hold */
//        mav.addObject(Constants.GENERIC_OBJ1,"variant");
//        mav.addObject(Constants.GENERIC_OBJECT_NAME1," Select Variant");
//        mav.addObject(Constants.GENERIC_FILTER_LIST,variants);


        return mav;
    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_INVENTORY_ADD_EDIT);

        List<InventoryCategory> inventoryCategoryList = this.categoryRepository.findAllByEnableTrue();

        List<Variant> variantList = this.variantRepository.findAllByEnableTrue();


        mav.addObject("variants", variantList);
        mav.addObject("inventoryCategoryList", inventoryCategoryList);
        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);

        //data access permission w.r.t. userLang
        String userLang= "ur";
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject("userLang",userLang);

        if (id != null && id > 0) {
            InventoryDTO inventoryDTO = this.findById(id);
            mav.addObject(Constants.RA_DTO, inventoryDTO);
        }
        else {
            InventoryDTO inventoryDTO = new InventoryDTO();
            mav.addObject(Constants.RA_DTO, inventoryDTO);
        }

        return mav;
    }

    public ModelAndView addUpdate(InventoryDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_INVENTORY_ADD_EDIT);

        List<InventoryCategory> inventoryCategoryList = this.categoryRepository.findAllByEnableTrue();

        List<Variant> variantList = this.variantRepository.findAllByEnableTrue();

        mav.addObject("variants", variantList);
        mav.addObject("inventoryCategoryList", inventoryCategoryList);
        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);

        //data access permission w.r.t. userLang
        String userLang= "ur";
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject("userLang",userLang);

        if (result.hasErrors()) {
            result.reject("valid.remove.errors");
            return mav;
        }

        this.save(dto);
        String message = confirmBox(dto);
        redirectAttributes.addFlashAttribute("message", message);

        mav = new ModelAndView("redirect:/inventories/viewAll");
        return mav;
    }
}



