//package com.cueballdb.repository;
//
//import com.cueballdb.model.Campaign;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@Repository
////@Transactional(readOnly = true)
//public class CampaignRepositoryImpl extends AbstractPersistenceManager<Campaign> implements CampaignCustomRepository {
//
//    @Override
//    public List<Campaign> findAllByFilters(String ou, String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count) {
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        StringBuilder where = new StringBuilder(" WHERE ");
//
//        if (ou != null) {
//            where.append(" FIND_IN_SET(operatingUnit.id, :ou) > 0 AND ");
//            parameters.put("ou", ou);
//        }
//
//        if(search != null && !search.equals("NaN")) {
//            where.append(" (title like :search OR description like :search OR imageUrl like :search OR romanUrl like :search OR id like :search) AND ");
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
//
//
//
//}
//
