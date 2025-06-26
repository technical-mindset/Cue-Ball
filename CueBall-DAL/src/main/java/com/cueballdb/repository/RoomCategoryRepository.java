package com.cueballdb.repository;


import com.cueballdb.model.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Integer>, RoomCategoryCustomRepository {
    RoomCategory findByName(String name);

    @Query("SELECT rc FROM RoomCategory rc where rc.enable = true AND rc.delete = false ORDER BY rc.id DESC")
    List<RoomCategory> findAllByEnableTrueAndDeleteFalse();
}
