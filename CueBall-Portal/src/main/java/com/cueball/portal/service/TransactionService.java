//package com.dawateislami.centraldonation.admin.service;
//
//import com.dawateislami.centraldonation.admin.dto.TransactionDTO;
//import com.dawateislami.centraldonation.admin.utils.Constants;
//import com.dawateislami.centraldonationdb.model.Bank;
//import com.dawateislami.centraldonationdb.model.Campaign;
//import com.dawateislami.centraldonationdb.model.OperatingUnit;
//import com.dawateislami.centraldonationdb.model.Transaction;
//import com.dawateislami.centraldonationdb.repository.BankRepository;
//import com.dawateislami.centraldonationdb.repository.CampaignRepository;
//import com.dawateislami.centraldonationdb.repository.OperatingUnitRepository;
//import com.dawateislami.centraldonationdb.repository.TransactionRepository;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TransactionService extends BaseService<Transaction, TransactionDTO, TransactionRepository> {
//    @Autowired
//    private OperatingUnitRepository operatingUnitRepository;
//    @Autowired
//    private BankRepository bankRepository;
//    @Autowired
//    private CampaignRepository campaignRepository;
//
//
//    public TransactionService(TransactionRepository repository) {
//        super(repository);
//    }
//
//    /**  Retrieving all transaction respective to the operating unit and as per filtration   */
//    public ModelAndView findFindAllView(String startDate, String endDate, Integer operatingUnit, Integer campaign, Integer bank, Integer successIndicator, Integer advertisement, String search , Integer enable, Integer pageSize, Integer pageNumber, boolean ajax) {
//        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_TRANSACTION_VIEW_ALL);
//        boolean errorMessage = false;
//
//
//        if(pageSize == null || pageSize <= 0 ) {
//            pageSize = Constants.MAX_PER_PAGE;
//        }
//        if(pageNumber == null || pageNumber <= 0) {
//            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
//        }
//
//        List<Transaction> lists = null;
//        List<OperatingUnit> operatingUnits = null;
//        List<Campaign> campaigns = null;
//        List<Bank> banks = this.bankRepository.findAllByEnableTrue();
//
//        long []count = {0};
//        String operatingUnitIds = this.getUserOperatingUnit();
//
//
//        /**  Retrieving Operating Units, Banks And Transactions according to which is allow to the user  **/
//        if (this.getUserId() != 1) {
//            operatingUnits = this.operatingUnitRepository.findAllByIds(operatingUnitIds);
//            campaigns = this.campaignRepository.findByOperatingUnitIds(operatingUnitIds);
//            lists = repository.findAllByFilters(startDate, endDate, operatingUnit, campaign, bank, successIndicator, advertisement, operatingUnitIds, search, enable,(pageNumber-1)*pageSize, pageSize, count);
//        }
//        else {
//            operatingUnits = this.operatingUnitRepository.findAllByEnableTrue();
//            campaigns = this.campaignRepository.findAll();
//            lists = repository.findAllByFilters(startDate, endDate, operatingUnit, campaign, bank, successIndicator, advertisement,null, search, enable,(pageNumber-1)*pageSize, pageSize, count);
//        }
//
//        /** For Popping-up the error message while the Transaction list is empty */
//        if (lists == null || lists.size() == 0) {
//            errorMessage = true;
//        }
//
//        List<TransactionDTO> DTOs = lists
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//
//        if (ajax) {
//            mav = new ModelAndView(Constants.RA_PAGE_TRANSACTION_VIEW_ALL_DETAIL);
//        }
//
//        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
//        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
//        mav.addObject("operatingUnits", operatingUnits);
//        mav.addObject("banks", banks);
//        mav.addObject("campaigns", campaigns);
//        mav.addObject("errorMessage",errorMessage);
//        mav.addObject("reportUrl", Constants.RA_BASE_URL + Constants.DONATION_PORT);
//        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
//        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
//        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
//        mav.addObject("totalCount", count[0]);
//        mav.addObject(Constants.RA_LIST, DTOs);
//        return mav;
//    }
//
//
//
//    @Override
//    public TransactionDTO mapEntityToDto(Transaction entity) {
//        TransactionDTO dto = new TransactionDTO();
//        BeanUtils.copyProperties(entity, dto);
//        String status = entity.getSuccessIndicator() == 0 ? "UNSUCCESS" : "SUCCESS";
//        boolean isAd = entity.getAdvertisement() == 0 ? false : true;
//
//        dto.setCreatedDate(entity.getCreatedDate().getTime());
//        dto.setName(entity.getDonorProfile().getName());
//        dto.setEmail(entity.getDonorProfile().getEmail());
//        dto.setContact(entity.getDonorProfile().getContact());
//        dto.setCampaignTitle(entity.getCampaign().getTitle());
//        dto.setBankName(entity.getBank().getName());
//        dto.setOperatingUnitTitle(entity.getOperatingUnit().getTitle());
//        dto.setSuccessIndicator(status);
//        dto.setAdvertisement(isAd);
//        return dto;
//    }
//
//    @Override
//    public Transaction mapDtoToEntity(TransactionDTO dto) {
//        return null;
//    }
//}
