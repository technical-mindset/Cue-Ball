package com.cueballdb.repository;

import com.cueballdb.model.Game;
import com.cueballdb.model.Restaurant;

import java.util.List;

public interface RestaurantCustomRepository {
    List<Restaurant> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count);
}
