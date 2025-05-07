package com.cueballdb.repository;

import com.cueballdb.model.TucBuying;

import java.util.List;

public interface TuckBuyingCustomRepository {
    List<TucBuying> findAllByFilterReport(String search, Integer enable, String inventoryId, String startDate, String endDate, Integer pageNumber, Integer pageSize, long[] count);
    List<TucBuying> findAllByFilterExcel(String search, Integer enable,String inventoryId, String startDate, String endDate);
}
