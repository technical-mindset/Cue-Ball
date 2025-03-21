//package com.cueballdb.repository;
//
//
//
//import com.cueballdb.model.Localization;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@Repository
////@Transactional(readOnly = true)
//public class LocalizationRepositoryImpl extends AbstractPersistenceManager<Localization> implements LocalizationRepositoryCustom{
//
//    @Override
//    public List<Localization> findAllByFilters(String lang, String search , Integer enable, Integer pageNumber, Integer pageSize, long[] count) {
//
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        StringBuilder where = new StringBuilder(" WHERE ");
//
//
//        if(search != null && !search.equals("NaN")) {
//            where.append(" (section like :search OR localText like :search OR id like :search) AND ");
//            parameters.put("search", "%" + search + "%");
//
//        }
//
//        if(enable != null  && enable > 0){
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
//        return getMaxResults( where+" ORDER BY id DESC ", parameters,pageNumber,pageSize,count);
//    }
//}
