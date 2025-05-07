package com.cueballdb.repository;

import com.cueballdb.model.Game;
import com.cueballdb.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer>, InventoryCustomRepository{
    long count();

    List<Inventory> findAllByEnableTrue();
}

