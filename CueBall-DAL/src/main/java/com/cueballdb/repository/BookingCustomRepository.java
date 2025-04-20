package com.cueballdb.repository;

import com.cueballdb.model.Booking;
import com.cueballdb.model.Game;

import java.util.List;

public interface BookingCustomRepository {
    List<Booking> findAllByFilters(String search, Integer enable, Integer roomId, Integer pageNumber, Integer pageSize, long[] count);
}
