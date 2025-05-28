package com.cueballdb.repository;

import com.cueballdb.model.Booking;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
            parameters.put("roomId", (roomId));
        }

        where.append("1=1");

        // ✅ Append GROUP BY clause before passing it to getMaxResults
//        where.append(" GROUP BY id");

        return getMaxResults(where+" ORDER BY id DESC ", parameters,pageNumber,pageSize,count);
    }

    @Override
    public List<Booking> findAllByFilterReport(String search, Integer enable, Integer roomId, Integer roomCategoryId, String startDate, String endDate, Integer pageNumber, Integer pageSize, long[] count) {

        List<Object> objects = combine(search, enable, roomId, roomCategoryId, startDate, endDate);
        return getMaxResults( (String) objects.get(0) +  " ORDER BY booking.id DESC ", (Map<String, Object>) objects.get(1), pageNumber, pageSize, count);
    }

    @Override
    public List<Booking> findAllByFilterExcel(String search, Integer enable, Integer roomId, Integer roomCategoryId, String startDate, String endDate) {

        List<Object> objects = combine(search, enable, roomId, roomCategoryId, startDate, endDate);
        return findByCriteria((String) objects.get(0) +  " ORDER BY booking.id DESC ", (Map<String, Object>) objects.get(1));
    }

    /** Usable Code */
    private List<Object> combine(String search, Integer enable, Integer roomId, Integer roomCategoryId, String startDate, String endDate){
        Map<String, Object> parameters = new HashMap<>();
        StringBuilder beforeWhere = new StringBuilder();
        StringBuilder where = new StringBuilder(" WHERE ");


        /** Adding manual joins (because no @ManyToOne mapping) */
        beforeWhere.append(" JOIN booking.room r ");
        beforeWhere.append(" JOIN r.roomCategory rc ");
        beforeWhere.append(" JOIN booking.customer c ");

        /** Between time-in, time-out, check-in, and check-out  */
        if ((!startDate.equals("NaN")) && !endDate.equals("NaN")) {
            where.append(" (booking.timeIn BETWEEN :startDate AND :endDate " +
                    "OR booking.timeOut BETWEEN :startDate AND :endDate " +
                    "OR booking.checkIn BETWEEN :startDate AND :endDate " +
                    "OR booking.checkOut BETWEEN :startDate AND :endDate) AND ");

            LocalDateTime startDateTime = LocalDateTime.parse(startDate); // Assuming startDate is in the format "yyyy-MM-dd'T'HH:mm"
            LocalDateTime endDateTime = LocalDateTime.parse(endDate);

            parameters.put("startDate", Timestamp.valueOf(startDateTime));
            parameters.put("endDate", Timestamp.valueOf(endDateTime));
        }


        /** For search in booking-title and customer-name */
        if (search != null && !search.equals("NaN")) {
            where.append(" (booking.title LIKE :search OR c.name LIKE :search) AND ");
            parameters.put("search", "%" + search.toLowerCase() + "%");
        }

        /** Fetching against the room */
        if (roomId != null && roomId > 0) {
            where.append(" booking.roomId = :roomId AND ");
            parameters.put("roomId", (roomId));
        }

        /** Fetching against the room-category */
        if (roomCategoryId != null && roomCategoryId > 0) {
            where.append(" rc.id = :roomCategoryId AND ");
            parameters.put("roomCategoryId", roomCategoryId);
        }

        if (enable != null) {
            if (enable == 1) {
                where.append(" booking.enable = :enable AND ");
                parameters.put("enable", true);
            } else if (enable == 2) {
                where.append(" booking.enable = :enable AND ");
                parameters.put("enable", false);
            }
        }


        where.append("1=1");

        // ✅ Append GROUP BY clause before passing it to getMaxResults
        where.append(" GROUP BY booking.id, r.id, rc.id, c.name ");

        return List.of(beforeWhere.toString() + where, parameters);
    }

}

