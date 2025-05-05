package com.cueballdb.repository;

import com.cueballdb.model.Game;
import com.cueballdb.model.Inventory;
import com.cueballdb.model.InventoryCategory;

import java.util.List;

public interface InventoryCustomRepository {
    List<Inventory> findAllByFilters(String search, Integer enable, Integer variantId, Integer restaurantId, Integer pageNumber, Integer pageSize, long[] count);

    List<Inventory> TuckShopSubMenuList(Integer id,Integer pageNumber, Integer pageSize, long[] count);
}
