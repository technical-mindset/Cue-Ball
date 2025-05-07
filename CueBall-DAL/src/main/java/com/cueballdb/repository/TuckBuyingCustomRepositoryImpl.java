package com.cueballdb.repository;

import com.cueballdb.model.Booking;
import com.cueballdb.model.TucBuying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class TuckBuyingCustomRepositoryImpl extends AbstractPersistenceManager<TucBuying> implements TuckBuyingCustomRepository {


    @Override
    public List<TucBuying> findAllByFilterReport(String search, Integer enable, String inventoryId, String startDate, String endDate, Integer pageNumber, Integer pageSize, long[] count) {
        List<Object> objects = combine(search, enable, inventoryId, startDate, endDate);
        return getMaxResults(objects.get(0) + " ORDER BY id DESC ", (Map<String, Object>) objects.get(1), pageNumber, pageSize, count);
    }
    @Override
    public List<TucBuying> findAllByFilterExcel(String search, Integer enable, String inventoryId, String startDate, String endDate) {

        List<Object> objects = combine(search, enable, inventoryId, startDate, endDate);
        return findByCriteria(objects.get(0) +  " ORDER BY id.id DESC ", (Map<String, Object>) objects.get(1));
    }

    /** Usable Code */
    private List<Object> combine(String search, Integer enable, String inventoryId, String startDate, String endDate) {
        Map<String, Object> parameters = new HashMap<>();
        StringBuilder where = new StringBuilder(" WHERE ");


        // Enable filter
        if (enable != null) {
            if (enable == 1) {
                where.append(" enable = :enable AND ");
                parameters.put("enable", true);
            } else if (enable == 2) {
                where.append(" enable = :enable AND ");
                parameters.put("enable", false);
            }
        }

        if(inventoryId != null && !inventoryId.equalsIgnoreCase("0")){
                where.append(" inventoryId = :inventoryId AND ");
                parameters.put("inventoryId", inventoryId);
        }

        // Search filter (by item name)
        if (search != null && !search.equalsIgnoreCase("NaN")) {
            where.append(" inventoryName LIKE :search AND ");
            parameters.put("search", "%" + search + "%");
        }

        // Date range filter (createdAt)
        if (startDate != null && endDate != null && !startDate.equals("NaN") && !endDate.equals("NaN")) {
            where.append(" DATE_FORMAT(createdAt, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND ");
            parameters.put("startDate", startDate);
            parameters.put("endDate", endDate);
        }

        where.append("1=1");
        return List.of(where.toString(), parameters);
    }



}

