//package com.dawateislami.centraldonation.admin.service;
//
//
//import com.dawateislami.centraldonation.admin.dto.OperatingUnitDTO;
//import com.dawateislami.centraldonation.admin.utils.Constants;
//import com.dawateislami.centraldonationdb.model.Bank;
//import com.dawateislami.centraldonationdb.model.Currency;
//import com.dawateislami.centraldonationdb.model.RoomCategory;
//import com.dawateislami.centraldonationdb.model.OperatingUnit;
//import com.dawateislami.centraldonationdb.repository.BankRepository;
//import com.dawateislami.centraldonationdb.repository.CurrencyRepository;
//import com.dawateislami.centraldonationdb.repository.LegalEntityRepository;
//import com.dawateislami.centraldonationdb.repository.OperatingUnitRepository;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Log4j2
//@Service
//public class OperatingUnitService extends BaseService<OperatingUnit, OperatingUnitDTO, OperatingUnitRepository> {
//
//    @Autowired
//    private CurrencyRepository currencyRepository;
//    @Autowired
//    private LegalEntityRepository legalEntityRepository;
//    @Autowired
//    private BankRepository bankRepository;
//
//    public OperatingUnitService(OperatingUnitRepository repository) {
//        super(repository);
//    }
//
//    @Override
//    public OperatingUnitDTO mapEntityToDto(OperatingUnit entity) {
//        OperatingUnitDTO dto = new OperatingUnitDTO();
//        BeanUtils.copyProperties(entity, dto);
//        dto.setCurrenciesIds(
//                entity.getCurrencies().stream()
//                        .map(Currency::getId)
//                        .collect(Collectors.toList()));
//
//        List<Integer> banks = Arrays.asList(entity.getBankIds().split(","))
//                .stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
//
//        dto.setBankIds(banks);
//        dto.setLegalEntityId(entity.getLegalEntity().getId());
//        dto.setLegalEntityTitle(entity.getLegalEntity().getTitle());
//        dto.setCreatedDate(entity.getCreatedDate().getTime());
//        dto.setModifyDate(entity.getModifyDate().getTime());
//        return dto;
//    }
//
//    @Override
//    public OperatingUnit mapDtoToEntity(OperatingUnitDTO dto) {
//        OperatingUnit entity = new OperatingUnit();
//
//        BeanUtils.copyProperties(dto, entity);
//
//        RoomCategory legalEntity = this.legalEntityRepository.findById(dto.getLegalEntityId()).get();
//        entity.setLegalEntity(legalEntity);
//
//        List<Currency> currencyList = this.currencyRepository.findAllById(dto.getCurrenciesIds());
//        entity.setCurrencies(currencyList);
//
//        String bankIds = dto.getBankIds().stream()
//                .map(String::valueOf)
//                .collect(Collectors.joining(","));
//
//        entity.setBankIds(bankIds);
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
//
//    public ModelAndView findFindAllView(String lang, String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
//        ModelAndView mav = new ModelAndView( Constants.RA_PAGE_OPERATING_UNIT_VIEW_ALL);
//
//        if(pageSize == null || pageSize <= 0 ) {
//            pageSize = Constants.MAX_PER_PAGE;
//        }
//        if(pageNumber == null || pageNumber <= 0) {
//            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
//        }
//
//        long []count = {0};
//        List<OperatingUnit> lists = null;
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
//        List<OperatingUnitDTO> DTOs = lists
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//
//        if (ajax) {
//            mav = new ModelAndView(Constants.RA_PAGE_OPERATING_UNIT_VIEW_ALL_DETAIL);
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
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_OPERATING_UNIT_ADD_EDIT);
//
////        List<Currency> AllCurrencies = this.currencyRepository.findAllByEnableTrue();
//        List<RoomCategory> legalEntities = this.legalEntityRepository.findAllByEnableTrue();
//        List<Bank> banks = this.bankRepository.findAllByEnableTrue();
//
//
//        mav.addObject("banks", banks);
//        mav.addObject("legalEntities", legalEntities);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        //data access permission w.r.t. userLang
//        String userLang= this.getUserOperatingUnit();
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//
//        if (id != null && id > 0) {
//            OperatingUnitDTO operatingUnitDTO = this.findById(id);
//            mav.addObject(Constants.RA_DTO, operatingUnitDTO);
//        }
//        else {
//            OperatingUnitDTO operatingUnitDTO = new OperatingUnitDTO();
//            mav.addObject(Constants.RA_DTO, operatingUnitDTO);
//        }
//
//        /**  Retrieving all the currency ids from the given list of banks */
//        String currencyIds = this.getCurrencyIds(banks);
//        List<Currency> AllCurrencies = this.currencyRepository.findCurrenciesByIds(currencyIds);
//
//        mav.addObject("currencies", AllCurrencies);
//        return mav;
//    }
//
//    public ModelAndView addUpdate(OperatingUnitDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_OPERATING_UNIT_ADD_EDIT);
//
////        List<Currency> AllCurrencies = this.currencyRepository.findAllByEnableTrue();
//        List<RoomCategory> legalEntities = this.legalEntityRepository.findAllByEnableTrue();
//        List<Bank> banks = this.bankRepository.findAllByEnableTrue();
//
//
//        /**  Retrieving all the currency ids from the given list of banks */
//        String currencyIds = this.getCurrencyIds(banks);
//        List<Currency> AllCurrencies = this.currencyRepository.findCurrenciesByIds(currencyIds);
//
//        mav.addObject("currencies", AllCurrencies);
//        mav.addObject("legalEntities", legalEntities);
//        mav.addObject("banks", banks);
//
//
//        //data access permission w.r.t. userLang
//        String userLang= this.getUserOperatingUnit();
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//
//        if (result.hasErrors()) {
//            result.reject("valid.remove.errors");
//            return mav;
//        }
//
//        this.save(dto);
//        String message = confirmBox(dto);
//        redirectAttributes.addFlashAttribute("message", message);
//
//        mav = new ModelAndView("redirect:/operatingunit/viewAll");
//        return mav;
//    }
//
//}
//
//
//
