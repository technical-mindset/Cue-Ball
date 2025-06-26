package com.cueballdb.repository;

import com.cueballdb.model.Inventory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class InventoryCustomRepositoryImpl extends AbstractPersistenceManager<Inventory> implements InventoryCustomRepository {

    @Override
    public List<Inventory> findAllByFilters(String search, Integer enable, Integer variantId, Integer restaurantId, Integer pageNumber, Integer pageSize, long[] count) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        StringBuilder where = new StringBuilder(" WHERE ");
        StringBuilder beforeWhere = new StringBuilder();

        if(search != null && !search.equals("NaN")) {
            where.append(" (inventory.name like :search OR inventoryCategory.name LIKE :search) AND ");
            parameters.put("search", "%" + search + "%");
        }

        if(enable != null){
            if(enable == 1) {
                where.append(" enable = :enable AND ");
                parameters.put("enable", true);

            } else if (enable == 2){
                where.append(" enable = :enable AND ");
                parameters.put("enable", false);
            }
        }

        if (variantId != null && variantId > 0) {
            where.append(" variantId = :variantid AND ");
            parameters.put("variantid", variantId);
        }

        if (restaurantId != null && restaurantId > 0) {
            where.append(" restaurantId = :restaurantId AND ");
            parameters.put("restaurantId", restaurantId);
        }

        where.append(" inventory.delete = false AND ");

        where.append("1=1");

        // ✅ Append GROUP BY clause before passing it to getMaxResults
//        where.append(" GROUP BY id ");

        return getMaxResults(beforeWhere.toString() + where + " ORDER BY id DESC ", parameters,pageNumber,pageSize,count);
    }

    @Override
    public List<Inventory> TuckShopSubMenuList(Integer id,Integer pageNumber, Integer pageSize, long[] count) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        StringBuilder where = new StringBuilder(" WHERE ");
        StringBuilder beforeWhere = new StringBuilder();

        if (id != null && id > 0) {
            where.append(" inventoryCategory.id = :icId AND ");
            parameters.put("icId", id);
        }

        where.append(" inventory.delete = false AND inventoryCategory.delete = false AND ");

        where.append("1=1");

//        return getMaxResults(beforeWhere.toString() + where + " ORDER BY id DESC ", parameters,pageNumber,pageSize,count);
        return findByCriteria(beforeWhere.toString() + where + " ORDER BY id DESC ", parameters);
    }
}

