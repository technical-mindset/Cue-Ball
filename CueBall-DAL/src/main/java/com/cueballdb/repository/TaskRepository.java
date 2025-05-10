package com.cueballdb.repository;

import com.cueballdb.model.Room;
import com.cueballdb.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
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

    long count();


    @Query("SELECT t FROM Task t WHERE t.complete = false AND t.createdAt <= :twoHoursAgo AND " +
            "(t.lastAlertSent IS NULL OR t.lastAlertSent <= :alertCutoff)")
    List<Task> findUncompletedTasksOlderThan2Hours(@Param("twoHoursAgo") Date twoHoursAgo,
                                                   @Param("alertCutoff") LocalDateTime alertCutoff);
}

