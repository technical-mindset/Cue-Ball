package com.cueballdb.repository;

import com.cueballdb.model.OperatingUnit;
import com.cueballdb.model.RoomCategory;

import java.util.List;

public interface RoomCategoryCustomRepository {
    List<RoomCategory> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count);
}
