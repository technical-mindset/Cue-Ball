//package com.dawateislami.centraldonation.admin.service;
//
//
//import com.dawateislami.centraldonation.admin.dto.CampaignDTO;
//import com.dawateislami.centraldonation.admin.utils.Constants;
//import com.dawateislami.centraldonationdb.model.Campaign;
//import com.dawateislami.centraldonationdb.model.OperatingUnit;
//import com.dawateislami.centraldonationdb.model.Role;
//import com.dawateislami.centraldonationdb.model.User;
//import com.dawateislami.centraldonationdb.repository.CampaignRepository;
//import com.dawateislami.centraldonationdb.repository.OperatingUnitRepository;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//
//@Log4j2
//@Service
//public class CampaignService extends BaseService<Campaign, CampaignDTO, CampaignRepository> {
//    @Autowired
//    private OperatingUnitRepository operatingUnitRepository;
//
//
//    public CampaignService(CampaignRepository repository) {
//        super(repository);
//    }
//
//    @Override
//    public CampaignDTO mapEntityToDto(Campaign entity) {
//        CampaignDTO dto = new CampaignDTO();
//        BeanUtils.copyProperties(entity, dto);
//
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        String startDate = null;
//        String endDate = null;
//
//        if(entity.getStartDate() != null){
//            startDate = df.format(entity.getStartDate());
//        }
//        if(entity.getEndDate() != null){
//            endDate = df.format(entity.getEndDate());
//        }
//
//        dto.setStartDate(startDate);
//        dto.setEndDate(endDate);
//
//        dto.setOperatingUnitId(entity.getOperatingUnit().getId());
//        dto.setCreatedDate(entity.getCreatedDate().getTime());
//        dto.setModifyDate(entity.getModifyDate().getTime());
//        return dto;
//    }
//
//    @Override
//    public Campaign mapDtoToEntity(CampaignDTO dto) {
//        Campaign entity = new Campaign();
//        BeanUtils.copyProperties(dto, entity);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//
//
//        OperatingUnit operatingUnit = this.operatingUnitRepository.findById(dto.getOperatingUnitId()).get();
//        entity.setOperatingUnit(operatingUnit);
//        entity.setLegalEntityId(operatingUnit.getLegalEntity().getId());
//
//
//        Date startDate = null;
//        Date endDate = null;
//
//        try {
//
//            if (!dto.isRoot()) {
//                startDate = dateFormat.parse(dto.getStartDate());
//                if (!dto.isLifeTime()) {
//                    endDate = dateFormat.parse(dto.getEndDate());
//                }
//            }
//
//            entity.setStartDate(startDate);
//            entity.setEndDate(endDate);
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//
//        if (dto.getId() > 0) {
//            entity.setModifyDate(new Date ( System.currentTimeMillis()));
//            entity.setCreatedDate(new Date (dto.getCreatedDate()));
//        } else {
//            entity.setCreatedDate(new Date ( System.currentTimeMillis()));
//            entity.setModifyDate(new Date ( System.currentTimeMillis()));
//        }
//        return entity;
//    }
//
//
//    public ModelAndView findFindAllView(String lang, String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
//        ModelAndView mav = new ModelAndView( Constants.RA_PAGE_CAMPAIGN_VIEW_ALL);
//
//
//        if(pageSize == null || pageSize <= 0 ) {
//            pageSize = Constants.MAX_PER_PAGE;
//        }
//        if(pageNumber == null || pageNumber <= 0) {
//            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
//        }
//
//
//        long []count = {0};
//        List<Campaign> lists = null;
//        String operatingUnit = this.getUserOperatingUnit();
//
//        /** data permission according to the user */
//        if (this.getUserId() != 1) {
//            lists = repository.findAllByFilters(operatingUnit, search, enable,(pageNumber-1)*pageSize, pageSize, count);
//        }
//        else {
//            lists = repository.findAllByFilters(null, search, enable,(pageNumber-1)*pageSize, pageSize, count);
//        }
//
//        List<CampaignDTO> DTOs = lists
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//
//        if (ajax) {
//            mav = new ModelAndView(Constants.RA_PAGE_CAMPAIGN_VIEW_ALL_DETAIL);
//        }
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
//        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
//        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
//        mav.addObject("totalCount", count[0]);
//        mav.addObject(Constants.RA_LIST, DTOs);
//        return mav;
//    }
//
//    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){
//
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_CAMPAIGN_ADD_EDIT);
//        String ou = this.getUserOperatingUnit();
//
//        /**  Retrieving Operating Units according to which is allow to the user  **/
//        if (this.getUserId() != 1) {
//            List<OperatingUnit> operatingUnits = this.operatingUnitRepository.findAllByIds(ou);
//            mav.addObject("operatingUnits", operatingUnits);
//        }
//        else {
//            List<OperatingUnit> operatingUnits = this.operatingUnitRepository.findAllByEnableTrue();
//            mav.addObject("operatingUnits", operatingUnits);
//        }
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        if (id != null && id > 0) {
//            CampaignDTO campaignDTO = this.findById(id);
//            mav.addObject(Constants.RA_DTO, campaignDTO);
//
////            if(userLang.contains(operatingUnitDTO.getLangCode())){
////            }
////            else{
////                mav = new ModelAndView("redirect:/imagegallery/viewAll");
////                redirectAttributes.addFlashAttribute("error",true);
////                redirectAttributes.addFlashAttribute("message", "Access Denied");
////            }
//        }
//        else {
//            CampaignDTO campaignDTO = new CampaignDTO();
//            mav.addObject(Constants.RA_DTO, campaignDTO);
//        }
//
//        return mav;
//    }
//
//    public ModelAndView addUpdate(CampaignDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {
//
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_CAMPAIGN_ADD_EDIT);
//        String ou = this.getUserOperatingUnit();
//        List<Campaign> campaign = null;
//
//        /**  Fetching Campaign against the given Root if exists then throws an error **/
//        if (dto.isRoot()) {
//            campaign = this.repository.findByRootAndOperatingUnit_Id(dto.getId(), dto.isRoot(), dto.getOperatingUnitId());
//            if (!campaign.isEmpty()) {
//                dto.setRoot(false);
//                result.rejectValue("root", "error.root", "Already Exists!");
//            }
//            else {
//                dto.setRomanUrl(""); // setting "" if the root has been checked.
//            }
//        }
//        /**  Fetching Campaign against the given Roman-Url if exists then throws an error **/
//        else {
//            campaign = this.repository.findByRomanUrlAndOperatingUnit_Id(dto.getId(), dto.getRomanUrl(), dto.getOperatingUnitId());
//            if (!campaign.isEmpty()) {
//                result.rejectValue("romanUrl", "error.romanUrl", Constants.RA_EDIT_MESSAGE);
//            }
//            else {
//                // validations for roman url
//                if (dto.getRomanUrl().length() < 4) {
//                    result.rejectValue("romanUrl", "error.romanUrl", Constants.RA_LENGTH_STRING);
//                }
//                else if (!isRomanUrlValid(dto.getRomanUrl(), Constants.RA_REGEX_ROMAN_URL)) {
//                    result.rejectValue("romanUrl", "error.romanUrl", Constants.RA_REGEX_STRING_URL);
//                }
//            }
//
//            /**  Checking the starting and ending or life-time check or root campaign  **/
//            if (!dto.isLifeTime()) {
//                if (dto.getStartDate() == null || dto.getStartDate().isEmpty()) {
//                    result.rejectValue("startDate", "error.startDate", Constants.RA_EMPTY_MESSAGE);
//                }
//                if (dto.getEndDate() == null || dto.getEndDate().isEmpty()) {
//                    result.rejectValue("endDate", "error.endDate", Constants.RA_EMPTY_MESSAGE);
//                }
//            }
//            else if (dto.getStartDate() == null || dto.getStartDate().isEmpty()) {
//                result.rejectValue("startDate", "error.startDate", Constants.RA_EMPTY_MESSAGE);
//            }
//        }
//
//
//        /**  Retrieving Operating Units according to which is allow to the user  **/
//        if (this.getUserId() != 1) {
//            List<OperatingUnit> operatingUnits = this.operatingUnitRepository.findAllByIds(ou);
//            mav.addObject("operatingUnits", operatingUnits);
//        }
//        else {
//            List<OperatingUnit> operatingUnits = this.operatingUnitRepository.findAllByEnableTrue();
//            mav.addObject("operatingUnits", operatingUnits);
//        }
//
//
//        /**  Checking the starting and ending or life-time check or root campaign  **/
////        if (!dto.isRoot()) {
////            if (!dto.isLifeTime()) {
////                if (dto.getStartDate() == null || dto.getStartDate().isEmpty()) {
////                    result.rejectValue("startDate", "error.startDate", Constants.RA_EMPTY_MESSAGE);
////                }
////                if (dto.getEndDate() == null || dto.getEndDate().isEmpty()) {
////                    result.rejectValue("endDate", "error.endDate", Constants.RA_EMPTY_MESSAGE);
////                }
////            }
////            else if (dto.getStartDate() == null || dto.getStartDate().isEmpty()) {
////                result.rejectValue("startDate", "error.startDate", Constants.RA_EMPTY_MESSAGE);
////            }
////        }
//
//        /**  Confirming at-least single field must be checked  **/
//        if (!dto.isSadqa() && !dto.isZakat()) {
//            if (!dto.isZakat()){
//                result.rejectValue("zakat","error.zakat", Constants.RA_EMPTY_MESSAGE);
//            }
//            if (!dto.isSadqa()) {
//                result.rejectValue("sadqa", "error.sadqa", Constants.RA_EMPTY_MESSAGE);
//            }
//        }
//
//        if (result.hasErrors()) {
//            result.reject("valid.remove.errors");
//            return mav;
//        }
//        this.save(dto);
//        String message = confirmBox(dto);
//        redirectAttributes.addFlashAttribute("message", message);
//
//        mav = new ModelAndView("redirect:/campaign/viewAll");
//        return mav;
//    }
//
//
//    /**  Method to check romanUrl against a provided regex  */
//    public static boolean isRomanUrlValid(String romanUrl, String regex) {
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(romanUrl);
//        return matcher.matches();
//    }
//
//}
//
//
