package com.cueballdb.repository;


import com.cueballdb.model.InventoryCategory;
import com.cueballdb.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer>, VariantCustomRepository {
    Variant findByName(String name);

    List<Variant> findAllByEnableTrue();
}
