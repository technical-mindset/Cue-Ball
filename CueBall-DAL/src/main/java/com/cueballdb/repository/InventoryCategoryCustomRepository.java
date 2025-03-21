package com.cueballdb.repository;

import com.cueballdb.model.InventoryCategory;
import com.cueballdb.model.RoomCategory;

import java.util.List;

public interface InventoryCategoryCustomRepository {
    List<InventoryCategory> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count);
}
