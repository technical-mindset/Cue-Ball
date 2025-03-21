//package com.dawateislami.centraldonation.admin.service;
//
//
//
//import com.dawateislami.centraldonation.admin.dto.LocalizationDTO;
//import com.dawateislami.centraldonation.admin.response.GenericListResponse;
//import com.dawateislami.centraldonation.admin.utils.Constants;
//import com.dawateislami.centraldonationdb.model.Localization;
//import com.dawateislami.centraldonationdb.repository.LocalizationRepository;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.BeanUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Log4j2
//@Service
//public class LocalizationService extends BaseService<Localization, LocalizationDTO, LocalizationRepository> {
//    public LocalizationService(LocalizationRepository repository) {
//        super(repository);
//    }
//
//    @Override
//    public LocalizationDTO mapEntityToDto( Localization entity) {
//        LocalizationDTO dto = new LocalizationDTO ();
//        BeanUtils.copyProperties(entity, dto);
//
//
//        dto.setCreatedDate(entity.getCreatedDate().getTime());
//        dto.setModifyDate (entity.getModifyDate ().getTime ());
//        return dto;
//    }
//
//    @Override
//    public Localization mapDtoToEntity( LocalizationDTO dto) {
//        Localization entity = new Localization ();
//
//        BeanUtils.copyProperties(dto, entity);
//
//
//        if (dto.getId() > 0) {
//            entity.setModifyDate ( new Date ( System.currentTimeMillis ()));
//            entity.setCreatedDate(new Date(dto.getCreatedDate()));
//        } else {
//            entity.setCreatedDate(new Date(System.currentTimeMillis()));
//            entity.setModifyDate ( new Date ( System.currentTimeMillis ()));
//        }
//
//
//        return entity;
//    }
//
//    public GenericListResponse findAll(Integer pageNumber, Integer pageSize, boolean page) {
//        List<LocalizationDTO> enTypeList = null;
//        if (page) {
//            Page<Localization> enTypes = repository.findAll(pageable(pageNumber, pageSize));
//            enTypeList = enTypes
//                    .stream()
//                    .map(this::mapEntityToDto)
//                    .collect(Collectors.toList());
//
//            return GenericListResponse.success(enTypeList, enTypes.getTotalPages());
//        } else {
//            enTypeList = repository.findAll()
//                    .stream()
//                    .map(this::mapEntityToDto)
//                    .collect(Collectors.toList());
//            return GenericListResponse.success(enTypeList, enTypeList.size());
//        }
//    }
//
//    public ModelAndView findFindAllView(String lang, String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
//
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_LOCALIZATION_VIEW_ALL);
//
//        if (pageSize == null || pageSize <= 0) {
//            pageSize = Constants.MAX_PER_PAGE;
//        }
//        if (pageNumber == null || pageNumber <= 0) {
//            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
//        }
//
//        //        Page<Localization> lists = repository.findAll(pageable(pageNumber, pageSize));
////                List<LocalizationDTO> DTOs = lists
////                .stream()
////                .map(this::mapEntityToDto)
////                .collect(Collectors.toList());
//
//        long []count = {0};
//        String userLang=langFilter(lang);
//
//        List<Localization> lists = repository.findAllByFilters(userLang,search,enable,(pageNumber-1)*pageSize,pageSize,count);
//        List<LocalizationDTO> DTOs = lists
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//
//        if (ajax) {
//            mav = new ModelAndView(Constants.RA_PAGE_LOCALIZATION_VIEW_ALL_DETAIL);
//        }
//
//
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
//        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
//        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
//        mav.addObject(Constants.RA_LIST, DTOs);
//        mav.addObject("totalCount", count[0]);
//        mav.addObject("baseUrl", Constants.SERVICE_BASE_URL);
//        return mav;
//    }
//
//    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_LOCALIZATION_ADD_EDIT);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        //data access permission w.r.t. userLang
////        String userLang = this.getUserLang();
////        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
////        mav.addObject("userLang",userLang);
//
//        if (id != null && id > 0) {
//            LocalizationDTO localizationDTO=this.findById(id);
////            if(userLang.contains(localizationDTO.getLangCode())){
//                mav.addObject(Constants.RA_DTO, localizationDTO);
////            }
////            else{
////                mav = new ModelAndView("redirect:/localization/viewAll");
////                redirectAttributes.addFlashAttribute("error",true);
////                redirectAttributes.addFlashAttribute("message", "Access Denied");
////            }
//        }
//        else {
//            LocalizationDTO localizationDTO = new LocalizationDTO ();
//            mav.addObject(Constants.RA_DTO, localizationDTO );
//        }
//
//        return mav;
//    }
//
//    public ModelAndView addUpdate( LocalizationDTO localizationDTO, BindingResult result, RedirectAttributes redirectAttributes) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_LOCALIZATION_ADD_EDIT);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        //data access permission w.r.t. userLang
////        String userLang= this.getUserLang();
////        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
////        mav.addObject("userLang",userLang);
//
//        if (result.hasErrors()) {
//            result.reject("valid.remove.errors");
//            return mav;
//        }
//        this.save( localizationDTO );
//        String message = confirmBox(localizationDTO);
//        redirectAttributes.addFlashAttribute("message", message);
//        mav = new ModelAndView("redirect:/localization/viewAll");
//        return mav;
//    }
//
//    public GenericListResponse findAllEnable() {
//        List<LocalizationDTO> dto = null;
//
//        dto = repository.findAll()
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect( Collectors.toList());
//        return GenericListResponse.success(dto, dto.size());
//    }
//}
