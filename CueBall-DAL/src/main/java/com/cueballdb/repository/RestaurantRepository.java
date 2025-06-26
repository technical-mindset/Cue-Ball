package com.cueballdb.repository;

import com.cueballdb.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, RestaurantCustomRepository {

    Restaurant findByTitle(String title);

    @Query("SELECT r FROM Restaurant r where r.enable = true AND r.delete = false ORDER BY r.id DESC")
    List<Restaurant> findAllByEnableTrueAndDelete();

    long count();

}

