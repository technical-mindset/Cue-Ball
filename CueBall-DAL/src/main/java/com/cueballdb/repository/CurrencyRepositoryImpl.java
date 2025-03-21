//package com.cueballdb.repository;
//
//import com.cueballdb.model.Currency;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@Repository
////@Transactional(readOnly = true)
//public class CurrencyRepositoryImpl extends AbstractPersistenceManager<Currency> implements CurrencyCustomRepository {
//
//    @Override
//    public List<Currency> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count) {
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        StringBuilder where = new StringBuilder(" WHERE ");
//
//        if(search != null && !search.equals("NaN")) {
//            where.append(" (name like :search OR currencyCode like :search OR id like :search) AND ");
//            parameters.put("search", "%" + search + "%");
//        }
//
//        if(enable != null){
//            if(enable == 1) {
//                where.append(" enable = :enable AND ");
//                parameters.put("enable", true);
//
//            } else if (enable == 2){
//                where.append(" enable = :enable AND ");
//                parameters.put("enable", false);
//            }
//        }
//
//        where.append("1=1");
//        return getMaxResults(where+" ORDER BY id DESC ", parameters,pageNumber,pageSize,count);
//    }
//
//    @Override
//    public Currency findByCurrencyCodeAndExpireDate(String currencyCode, long expiry) {
//        Map<String, Object> params = new HashMap<String, Object> ();
//        params.put("currency", currencyCode);
//        params.put("expireDate", expiry);
//
//        String where = " WHERE  currency_code = :currency";
//        where += " And expireDate >= :expireDate ";
//
//        List<Currency> currencies = findByCriteria(where, params);
//
//        if (currencies != null && currencies.size() > 0) {
//            return currencies.get(0);
//        }
//
//        return null;
//    }
//}
//
