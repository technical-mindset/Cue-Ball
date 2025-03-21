//package com.cueballdb.repository;
//
//import com.cueballdb.model.Bank;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@Repository
////@Transactional(readOnly = true)
//public class BankRepositoryImpl extends AbstractPersistenceManager<Bank> implements BankCustomRepository {
//
//    @Override
//    public List<Bank> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count) {
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        StringBuilder where = new StringBuilder(" WHERE ");
//
//        if(search != null && !search.equals("NaN")) {
//            where.append(" (name like :search OR id like :search) AND ");
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
//}
//
