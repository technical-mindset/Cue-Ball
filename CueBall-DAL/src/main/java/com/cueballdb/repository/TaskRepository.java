package com.cueballdb.repository;

import com.cueballdb.model.Room;
import com.cueballdb.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, TaskCustomRepository {
    List<Task> findAllByCompleteTrue();

    List<Task> findAllByEnableTrue();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.taskDate = CURRENT_DATE AND t.shift LIKE :shift AND (:completed IS NULL OR t.complete = :completed) AND (:userId IS NULL OR t.userId = :userId)")
    long countTasksByShiftToday(@Param("shift") String shift, Boolean completed, Integer userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.taskDate = CURRENT_DATE AND (:completed IS NULL OR t.complete = :completed)")
    long countTasksByShiftTodayForAdmin(Boolean completed);


//    @Query("SELECT t FROM Task t WHERE t.complete = false AND t.createdAt <= :twoHoursAgo AND " +
//            "(t.lastAlertSent IS NULL OR t.lastAlertSent <= :alertCutoff)")
//    List<Task> findUncompletedTasksOlderThan2Hours(@Param("twoHoursAgo") Date twoHoursAgo,
//                                                   @Param("alertCutoff") LocalDateTime alertCutoff);

    @Query("SELECT t FROM Task t WHERE t.complete = false " +
            "AND t.createdAt <= :twoHoursAgo " +
            "AND t.createdAt >= :startOfDay " +
            "AND (t.lastAlertSent IS NULL OR t.lastAlertSent <= :alertCutoff)")
    List<Task> findUncompletedTasksOlderThan2HoursForToday(
            @Param("twoHoursAgo") Date twoHoursAgo,
            @Param("startOfDay") Date startOfDay,
            @Param("alertCutoff") LocalDateTime alertCutoff
    );

    @Modifying
    @Query("UPDATE Task n SET n.complete = ?2, n.modifiedAt = current_timestamp WHERE id = ?1")
    int updateTaskStatus(int id, boolean complete);

}

