package com.cueballdb.repository;

import com.cueballdb.model.Game;
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
    public List<Inventory> findAllByFilters(String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        StringBuilder where = new StringBuilder(" WHERE ");

        if(search != null && !search.equals("NaN")) {
            where.append(" (name like :search OR inventoryCategory.name LIKE :search) AND ");
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

        where.append("1=1");

        // ✅ Append GROUP BY clause before passing it to getMaxResults
        where.append(" GROUP BY id, name ");

        return getMaxResults(where+" ORDER BY id DESC ", parameters,pageNumber,pageSize,count);
    }
}

