package com.cueballdb.repository;

import com.cueballdb.model.Game;

import java.util.List;

public interface GameCustomRepository {
    List<Game> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count);
}
