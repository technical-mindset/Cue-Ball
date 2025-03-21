//package com.dawateislami.centraldonation.admin.service;
//
//import com.dawateislami.centraldonation.admin.dto.BankDTO;
//import com.dawateislami.centraldonation.admin.dto.LegalEntityDTO;
//import com.dawateislami.centraldonation.admin.utils.Constants;
//import com.dawateislami.centraldonationdb.model.Bank;
//import com.dawateislami.centraldonationdb.model.Currency;
//import com.dawateislami.centraldonationdb.model.OperatingUnit;
//import com.dawateislami.centraldonationdb.repository.BankRepository;
//import com.dawateislami.centraldonationdb.repository.CurrencyRepository;
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
//public class BankService extends BaseService<Bank, BankDTO, BankRepository> {
//
//    @Autowired
//    OperatingUnitRepository operatingUnitRepository;
//
//    @Autowired
//    private CurrencyRepository currencyRepository;
//
//    public BankService(BankRepository repository) {
//        super(repository);
//    }
//
//    @Override
//    public BankDTO mapEntityToDto(Bank entity) {
//        BankDTO dto = new BankDTO();
//        BeanUtils.copyProperties(entity, dto);
//
//        List<Integer> currencies = null;
//
//        if(!entity.getCurrencyId().equalsIgnoreCase("")){
//            currencies = Arrays.asList(entity.getCurrencyId().split(",")).stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
//        }
//
//        dto.setCurrencyId(currencies);
//        dto.setCreatedDate(entity.getCreatedDate().getTime());
////        dto.setModifyDate(entity.getModifyDate().getTime());
//        return dto;
//    }
//
//    @Override
//    public Bank mapDtoToEntity(BankDTO dto) {
//        Bank entity = new Bank();
//        BeanUtils.copyProperties(dto, entity);
//
//        String currencyIds = dto.getCurrencyId().stream()
//                .map(String::valueOf)
//                .collect(Collectors.joining(","));
//
//        entity.setCurrencyId(currencyIds);
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
//        ModelAndView mav = new ModelAndView( Constants.RA_PAGE_BANK_VIEW_ALL);
//
//        if(pageSize == null || pageSize <= 0 ) {
//            pageSize = Constants.MAX_PER_PAGE;
//        }
//        if(pageNumber == null || pageNumber <= 0) {
//            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
//        }
//
//        long []count = {0};
//        List<Bank> lists = repository.findAllByFilters(search,enable,(pageNumber-1)*pageSize,pageSize,count);
//        List<BankDTO> DTOs = lists
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//
//        if (ajax) {
//            mav = new ModelAndView(Constants.RA_PAGE_BANK_VIEW_ALL_DETAIL);
//        }
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
//        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
//        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
//        mav.addObject("totalCount", count[0]);
//        mav.addObject(Constants.RA_LIST, DTOs);
//
//        return mav;
//    }
//
//    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){
//
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_BANK_ADD_EDIT);
//
//        List<Currency> AllCurrencies = this.currencyRepository.findAllByEnableTrue();
//
//        //data access permission w.r.t. userLang
//        String userLang= this.getUserOperatingUnit();
//
//        if (id != null && id > 0) {
//            BankDTO bankDTO = this.findById(id);
//            mav.addObject(Constants.RA_DTO, bankDTO);
////            if(userLang.contains(legalEntityDTO.getLangCode())){
////                mav.addObject(Constants.RA_DTO, mediaDTO);
////            }
////            else{
////                mav = new ModelAndView("redirect:/media/viewAll");
////                redirectAttributes.addFlashAttribute("error",true);
////                redirectAttributes.addFlashAttribute("message", "Access Denied");
////            }
//        }
//        else {
//            BankDTO bankDTO = new BankDTO ();
//            mav.addObject(Constants.RA_DTO, bankDTO);
//        }
//
//        /**  Bank Vendors */
//        mav.addObject("vendors", vendors());
//
//        /**  Formula Type */
//        mav.addObject("formulaType", Arrays.asList("reminder", "percentage"));
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//        mav.addObject("currencies", AllCurrencies);
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//
//        return mav;
//    }
//
//    public ModelAndView addUpdate(BankDTO bankDTO, BindingResult result, RedirectAttributes redirectAttributes) {
//
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_BANK_ADD_EDIT);
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//
//        List<Currency> AllCurrencies = this.currencyRepository.findAllByEnableTrue();
//        mav.addObject("currencies", AllCurrencies);
//
//        //data access permission w.r.t. userLang
//        String userLang= this.getUserOperatingUnit();
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("userLang",userLang);
//
//        /**  Bank Vendors */
//        mav.addObject("vendors", vendors());
//
//        /**  Formula Type */
//        mav.addObject("formulaType", Arrays.asList("reminder", "percentage"));
//
//
//        /**  Fetching Bank against the addition / updation of Bank if exists then throws an error **/
//        Bank currency = repository.findByName(bankDTO.getName());
//        if(currency != null && bankDTO.getId() != currency.getId()){
//            result.rejectValue("name", "name.root", "Bank already exists!");
//        }
//
//        /**  When anyone can disable a bank, it checks whether the bank is selected in any operating unit or not then popping-up the error respectively  */
//        Integer operatingUnitCount = operatingUnitRepository.findByBankContains(bankDTO.getId());
//        if (operatingUnitCount > 0 && !bankDTO.isEnable()) {
//            result.rejectValue("enable", "error.enable", Constants.RA_DEFAULT_BANK_MESSAGE);
//        }
//
//        if (result.hasErrors()) {
//            return mav;
//        }
//
//        this.save(bankDTO);
//        String message = confirmBox(bankDTO);
//        redirectAttributes.addFlashAttribute("message", message);
//
//        mav = new ModelAndView("redirect:/bank/viewAll");
//        return mav;
//    }
//
//
//}
//
//
//
//
