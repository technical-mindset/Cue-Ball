package com.cueballdb.repository;

import com.cueballdb.model.Booking;

import java.util.List;

public interface BookingCustomRepository {
    List<Booking> findAllByFilters(String search, Integer enable, Integer roomId, Integer pageNumber, Integer pageSize, long[] count);

    List<Booking> findAllByFilterReport(String search, Integer enable, Integer roomId, Integer roomCategoryId, String startDate, String endDate, Integer pageNumber, Integer pageSize, long[] count);
    List<Booking> findAllByFilterExcel(String search, Integer enable, Integer roomId, Integer roomCategoryId, String startDate, String endDate);
}
