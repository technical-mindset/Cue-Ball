package com.cueballdb.repository;

import com.cueballdb.model.Booking;
import com.cueballdb.model.Game;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class BookingCustomRepositoryImpl extends AbstractPersistenceManager<Booking> implements BookingCustomRepository {

    @Override
    public List<Booking> findAllByFilters(String search, Integer enable, Integer roomId, Integer pageNumber, Integer pageSize, long[] count) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        StringBuilder where = new StringBuilder(" WHERE ");

        if(search != null && !search.equals("NaN")) {
            where.append(" (title like :search) AND ");
            parameters.put("search", "%" + search + "%");
        }

        if(enable != null){
            if(enable == 1) {
                where.append(" enable = :enable AND ");
                parameters.put("enable", true);

            } else if (enable == 2){
                where.append(" enable = :enable AND ");
                parameters.put("enable", false);
            }
        }

        if (roomId != null && roomId > 0) {
            where.append(" roomId = :roomId AND ");
            parameters.put("roomId", String.valueOf(roomId));
        }

        where.append("1=1");

        // ✅ Append GROUP BY clause before passing it to getMaxResults
        where.append(" GROUP BY id");

        return getMaxResults(where+" ORDER BY id DESC ", parameters,pageNumber,pageSize,count);
    }
}

