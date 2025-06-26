package com.cueballdb.repository;


import com.cueballdb.model.InventoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryCategoryRepository extends JpaRepository<InventoryCategory, Integer>, InventoryCategoryCustomRepository {
    InventoryCategory findByName(String name);

    @Query("SELECT category FROM InventoryCategory category WHERE enable = true AND category.delete = false ORDER BY id DESC")
    List<InventoryCategory> findAllByEnableTrueAndDeleteFalse();
}
