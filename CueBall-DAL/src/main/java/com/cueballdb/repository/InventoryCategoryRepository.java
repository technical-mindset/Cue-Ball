package com.cueballdb.repository;


import com.cueballdb.model.InventoryCategory;
import com.cueballdb.model.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryCategoryRepository extends JpaRepository<InventoryCategory, Integer>, InventoryCategoryCustomRepository {
    InventoryCategory findByName(String name);

    List<InventoryCategory> findAllByEnableTrue();
}
