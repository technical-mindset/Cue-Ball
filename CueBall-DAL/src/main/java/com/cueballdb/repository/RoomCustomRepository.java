package com.cueballdb.repository;

import com.cueballdb.model.Game;
import com.cueballdb.model.Room;

import java.util.List;

public interface RoomCustomRepository {
    List<Room> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count);
}
