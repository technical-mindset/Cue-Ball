//package com.cueballdb.repository;
//
//import com.cueballdb.model.Transaction;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@Repository
////@Transactional(readOnly = true)
//public class TransactionRepositoryImpl extends AbstractPersistenceManager<Transaction> implements TransactionCustomRepository {
//
//    public List<Transaction> findAllByFilters(String startDate, String endDate,Integer operatingUnit, Integer campaign, Integer bank, Integer successIndicator, Integer advertisement,
//                                              String ou, String search, Integer enable, Integer pageNumber, Integer pageSize, long[] count) {
//        Map<String, Object> parameters = new HashMap<>();
//
//        StringBuilder where = new StringBuilder(" WHERE ");
//        StringBuilder beforeWhere = new StringBuilder();
//
//        /**  Adding joins on bank, operating_unit, and campaign tables  */
//        beforeWhere.append(" JOIN transaction.bank AS b ")
//                .append(" JOIN transaction.operatingUnit AS ou ")
//                .append(" JOIN transaction.campaign AS c")
//                .append(" JOIN transaction.donorProfile AS dp ");
//
//        /**  Filtering by operating unit  */
//        if (ou != null) {
//            where.append(" FIND_IN_SET(ou.id, :ou) > 0 AND ");
//            parameters.put("ou", ou);
//        }
//
//        if ((!startDate.equals("NaN")) && !endDate.equals("NaN")) {
//            where.append("  DATE_FORMAT(transaction.createdDate, '%Y-%m-%d') BETWEEN  :startDate AND :endDate  AND ");
//            parameters.put("startDate", startDate);
//            parameters.put("endDate", endDate);
//        }
//
//        if (operatingUnit != null && operatingUnit > 0) {
//            where.append(" ou.id = :operatingUnit AND ");
//            parameters.put("operatingUnit", operatingUnit);
//        }
//
//        if (campaign != null && campaign > 0) {
//            where.append(" c.id = :campaign AND ");
//            parameters.put("campaign", campaign);
//        }
//
//        if (bank != null && bank > 0) {
//            where.append(" b.id = :bank AND ");
//            parameters.put("bank", bank);
//        }
//
//        if (successIndicator != null && successIndicator > 0){
//            if (successIndicator == 1){
//                where.append(" transaction.successIndicator = :status AND ");
//                parameters.put("status", 1);
//            }
//            else if (successIndicator == 2) {
//                where.append(" transaction.successIndicator = :status AND ");
//                parameters.put("status", 0);
//            }
//        }
//
//        if (advertisement != null && advertisement > 0){
//            if (advertisement == 1){
//                where.append(" transaction.advertisement = :advertisement AND ");
//                parameters.put("advertisement", 1);
//            }
//            else if (advertisement == 2) {
//                where.append(" transaction.advertisement = :advertisement AND ");
//                parameters.put("advertisement", 0);
//            }
//        }
//
//
//        /**  Search filter   */
//        if (search != null && !search.equals("NaN")) {
//            where.append(" (transaction.countryCode LIKE :search OR dp.name LIKE :search OR dp.email LIKE :search OR dp.contact LIKE :search OR transaction.isoCurrencyCode LIKE :search OR DATE_FORMAT(transaction.createdDate, '%Y-%m-%d') LIKE :search OR b.name LIKE :search OR c.title LIKE :search OR ou.title LIKE :search OR transaction.type LIKE :search OR transaction.id LIKE :search OR transaction.successIndicator LIKE :search OR transaction.bankTransactionId LIKE :search OR transaction.bankTransactionStatus LIKE :search OR transaction.bankDescription LIKE :search OR transaction.bankResponseCode LIKE :search) AND ");
//            parameters.put("search", "%" + search + "%");
//        }
//
//        /**  Enable filter */
//        if (enable != null) {
//            if (enable == 1) {
//                where.append(" transaction.enable = :enable AND ");
//                parameters.put("enable", true);
//            } else if (enable == 2) {
//                where.append(" transaction.enable = :enable AND ");
//                parameters.put("enable", false);
//            }
//        }
//
//        where.append("1=1");
//        /**  Executing the query with pagination and counting the results  */
//        return getMaxResults(beforeWhere.toString() + where.toString() + " ORDER BY transaction.id DESC", parameters, pageNumber, pageSize, count);
//    }
//
//
//    public List<Transaction> findAllByFiltersForReport(String startDate, String endDate, Integer operatingUnit, Integer campaign, Integer bank, Integer successIndicator, Integer advertisement, String ou, String search, Integer enable){
//        Map<String, Object> parameters = new HashMap<>();
//
//        StringBuilder where = new StringBuilder(" WHERE ");
//        StringBuilder beforeWhere = new StringBuilder();
//
//        /**  Adding joins on bank, operating_unit, and campaign tables  */
//        beforeWhere.append(" JOIN transaction.bank AS b ")
//                .append(" JOIN transaction.operatingUnit AS ou ")
//                .append(" JOIN transaction.campaign AS c")
//                .append(" JOIN transaction.donorProfile AS dp ");
//
//        /**  Filtering by operating unit  */
//        if (ou != null) {
//            where.append(" FIND_IN_SET(ou.id, :ou) > 0 AND ");
//            parameters.put("ou", ou);
//        }
//
//        if ((!startDate.equals("NaN")) && !endDate.equals("NaN")) {
//            where.append("  DATE_FORMAT(transaction.createdDate, '%Y-%m-%d') BETWEEN  :startDate AND :endDate  AND ");
//            parameters.put("startDate", startDate);
//            parameters.put("endDate", endDate);
//        }
//
//        if (operatingUnit != null && operatingUnit > 0) {
//            where.append(" ou.id = :operatingUnit AND ");
//            parameters.put("operatingUnit", operatingUnit);
//        }
//
//        if (campaign != null && campaign > 0) {
//            where.append(" c.id = :campaign AND ");
//            parameters.put("campaign", campaign);
//        }
//
//        if (bank != null && bank > 0) {
//            where.append(" b.id = :bank AND ");
//            parameters.put("bank", bank);
//        }
//
//        if (successIndicator != null && successIndicator > 0){
//            if (successIndicator == 1){
//                where.append(" transaction.successIndicator = :status AND ");
//                parameters.put("status", 1);
//            }
//            else if (advertisement == 2) {
//                where.append(" transaction.successIndicator = :status AND ");
//                parameters.put("status", 0);
//            }
//        }
//
//        if (advertisement != null && advertisement > 0){
//            if (advertisement == 1){
//                where.append(" transaction.advertisement = :advertisement AND ");
//                parameters.put("advertisement", 1);
//            }
//            else if (advertisement == 2) {
//                where.append(" transaction.advertisement = :advertisement AND ");
//                parameters.put("advertisement", 0);
//            }
//        }
//
//        /**  Search filter   */
//        if (search != null && !search.equals("NaN")) {
//            where.append(" (transaction.countryCode LIKE :search OR dp.name LIKE :search OR dp.email LIKE :search OR dp.contact LIKE :search OR transaction.isoCurrencyCode LIKE :search OR DATE_FORMAT(transaction.createdDate, '%Y-%m-%d') LIKE :search OR b.name LIKE :search OR c.title LIKE :search OR ou.title LIKE :search OR transaction.type LIKE :search OR transaction.id LIKE :search OR transaction.successIndicator LIKE :search) AND ");
//            parameters.put("search", "%" + search + "%");
//        }
//
//        /**  Enable filter */
//        if (enable != null) {
//            if (enable == 1) {
//                where.append(" transaction.enable = :enable AND ");
//                parameters.put("enable", true);
//            } else if (enable == 2) {
//                where.append(" transaction.enable = :enable AND ");
//                parameters.put("enable", false);
//            }
//        }
//
//        where.append("1=1");
//        /**  Executing the query with pagination and counting the results  */
//        return findByCriteria(beforeWhere.toString() + where.toString() + " ORDER BY transaction.id DESC", parameters);
//    }
//
//
//}
//
