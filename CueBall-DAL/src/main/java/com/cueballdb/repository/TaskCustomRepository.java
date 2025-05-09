package com.cueballdb.repository;

import com.cueballdb.model.Room;
import com.cueballdb.model.Task;

import java.util.List;

public interface TaskCustomRepository {
    List<Task> findAllByFilters(String search, Integer enable, Integer complete, Integer userId, String shift, boolean isTaskDateEnable, Integer pageNumber, Integer pageSize, long[] count);
}
