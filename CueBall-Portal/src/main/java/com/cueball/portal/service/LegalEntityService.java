//package com.dawateislami.centraldonation.admin.service;
//
//import com.dawateislami.centraldonation.admin.dto.LegalEntityDTO;
//import com.dawateislami.centraldonation.admin.utils.Constants;
//import com.dawateislami.centraldonationdb.model.RoomCategory;
//import com.dawateislami.centraldonationdb.repository.LegalEntityRepository;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Log4j2
//@Service
//public class LegalEntityService extends BaseService<RoomCategory, LegalEntityDTO, LegalEntityRepository> {
//
//    public LegalEntityService(LegalEntityRepository repository) {
//        super(repository);
//    }
//
//    @Override
//    public LegalEntityDTO mapEntityToDto(RoomCategory entity) {
//        LegalEntityDTO dto = new LegalEntityDTO();
//        BeanUtils.copyProperties(entity, dto);
//
//
//        dto.setCreatedDate(entity.getCreatedDate().getTime());
//        dto.setModifyDate(entity.getModifyDate().getTime());
//        return dto;
//    }
//
//    @Override
//    public RoomCategory mapDtoToEntity(LegalEntityDTO dto) {
//        RoomCategory entity = new RoomCategory();
//        BeanUtils.copyProperties(dto, entity);
//
//        if (dto.getId() > 0) {
//            entity.setModifyDate(new Date ( System.currentTimeMillis()));
//            entity.setCreatedDate(new Date(dto.getCreatedDate()));
//        } else {
//            entity.setCreatedDate(new Date ( System.currentTimeMillis()));
//            entity.setModifyDate(new Date ( System.currentTimeMillis()));
//        }
//        return entity;
//    }
//
//    public ModelAndView addUpdate(LegalEntityDTO legalEntityDTO, BindingResult result, RedirectAttributes redirectAttributes) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_LEGAL_ENTITY_ADD_EDIT);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        //data access permission w.r.t. userLang
//        String userLang= this.getUserOperatingUnit();
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//
//        if (result.hasErrors()) {
//            return mav;
//        }
//        this.save(legalEntityDTO);
//        String message = confirmBox(legalEntityDTO);
//        redirectAttributes.addFlashAttribute("message", message);
//
//        mav = new ModelAndView("redirect:/legalentity/viewAll");
//        return mav;
//    }
//
//    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){
//
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_LEGAL_ENTITY_ADD_EDIT);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//
//        //data access permission w.r.t. userLang
//        String operatingUnit = this.getUserOperatingUnit();
//
//        if (id != null && id > 0) {
//            LegalEntityDTO legalEntityDTO = this.findById(id);
//            mav.addObject(Constants.RA_DTO, legalEntityDTO);
////            if(userLang.contains(legalEntityDTO.getLangCode())){
////                mav.addObject(Constants.RA_DTO, mediaDTO);
////            }
////            else{
////                mav = new ModelAndView("redirect:/media/viewAll");
////                redirectAttributes.addFlashAttribute("error",true);
////                redirectAttributes.addFlashAttribute("message", "Access Denied");
////            }
//        }
//        else
//            {
//            LegalEntityDTO mediaDTO = new LegalEntityDTO ();
//            mav.addObject(Constants.RA_DTO, mediaDTO);
//        }
//
//        return mav;
//    }
//
//    public ModelAndView findFindAllView(String search , Integer enable, Integer pageSize, Integer pageNumber, boolean ajax) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_LEGAL_ENTITY_VIEW_ALL);
//
//        if(pageSize == null || pageSize <= 0 ) {
//            pageSize = Constants.MAX_PER_PAGE;
//        }
//        if(pageNumber == null || pageNumber <= 0) {
//            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
//        }
//        long []count = {0};
//        String userOU = this.getUserOperatingUnit();
//
//        List<RoomCategory> lists = repository.findAllByFilters(userOU, search, enable,(pageNumber-1)*pageSize, pageSize, count);
//        List<LegalEntityDTO> DTOs = lists
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//
//        if (ajax) {
//            mav = new ModelAndView(Constants.RA_PAGE_LEGAL_ENTITY_VIEW_ALL_DETAIL);
//        }
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
////        mav.addObject("userLang",userLang);
//        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
//        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
//        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
//        mav.addObject("totalCount", count[0]);
//        mav.addObject(Constants.RA_LIST, DTOs);
//        return mav;
//    }
//
//}
