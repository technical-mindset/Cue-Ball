package com.cueballdb.repository;

import com.cueballdb.model.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class TaskCustomRepositoryImpl extends AbstractPersistenceManager<Task> implements TaskCustomRepository {

    @Override
    public List<Task> findAllByFilters(String search, Integer enable, Integer complete, Integer userId, String shift, boolean isTaskDateEnable, Integer pageNumber, Integer pageSize, long[] count) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        StringBuilder where = new StringBuilder(" WHERE ");

        if(search != null && !search.equals("NaN")) {
            where.append(" (description like :search) AND ");
            parameters.put("search", "%" + search + "%");
        }

        /** Fetching against the user */
        if (userId != null && userId > 0) {
            where.append(" task.userId =:userId AND ");
            parameters.put("userId", userId);
        }

        /** Fetching against shift */
        if (shift != null && !shift.equals("NaN")) {
            where.append(" task.shift LIKE :shift AND ");
            parameters.put("shift", shift);
        }

        /** Fetching against enable */
        if(enable != null){
            if(enable == 1) {
                where.append(" task.enable = :enable AND ");
                parameters.put("enable", true);

            } else if (enable == 2){
                where.append(" task.enable = :enable AND ");
                parameters.put("enable", false);
            }
        }

        /** Fetching against complete */
        if(complete != null){
            if(complete == 1) {
                where.append(" task.complete = :complete AND ");
                parameters.put("complete", true);

            } else if (complete == 2){
                where.append(" task.complete = :complete AND ");
                parameters.put("complete", false);
            }
        }

        /** Fetching against task-date is current-date */
        if (isTaskDateEnable) {
            where.append(" CAST(task.taskDate AS date) = :date AND ");
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate); // 👈 convert LocalDate to java.util.Date
            parameters.put("date", date);
        }


        where.append("1=1");

        // ✅ Append GROUP BY clause before passing it to getMaxResults
//        where.append(" GROUP BY task.id ");

        return getMaxResults( where + " ORDER BY task.id DESC ", parameters,pageNumber,pageSize,count);
    }
}

