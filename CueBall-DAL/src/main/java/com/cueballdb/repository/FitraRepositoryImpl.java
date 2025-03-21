//package com.cueballdb.repository;
//
//import com.cueballdb.model.FitraRateByCurrency;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@Repository
////@Transactional(readOnly = true)
//public class FitraRepositoryImpl extends AbstractPersistenceManager<FitraRateByCurrency> implements FitraCustomRepository {
//    @Override
//    public FitraRateByCurrency findByCountry(String country){
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("country", country);
//
//
//        System.out.println("****************in query " + country);
//
//        String where = " WHERE  country =:country ";
//
//
//        List<FitraRateByCurrency> fitraRateByCurrencies = findByCriteria(where, params);
//        System.out.println("****************in query " + fitraRateByCurrencies.size());
//        if (fitraRateByCurrencies != null && fitraRateByCurrencies.size() > 0) {
//            return fitraRateByCurrencies.get(0);
//        }
//
//        return null;
//
//    }
//}
