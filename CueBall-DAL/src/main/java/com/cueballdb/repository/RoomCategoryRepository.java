package com.cueballdb.repository;


import com.cueballdb.model.Role;
import com.cueballdb.model.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Integer>, RoomCategoryCustomRepository {
    RoomCategory findByName(String name);

    List<RoomCategory> findAllByEnableTrue();
}
