package com.cueballdb.repository;

import com.cueballdb.model.InventoryCategory;
import com.cueballdb.model.Variant;

import java.util.List;

public interface VariantCustomRepository {
    /**  For Admin  */
    List<Variant> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count);
    /** For Normal User  */
    public List<Variant> findAllByFiltersAndDeleted(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count);

    }
