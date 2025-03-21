//package com.cueballdb.repository;
//
//import com.cueballdb.model.Transaction;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
////@Repository
//public interface TransactionRepository extends JpaRepository<Transaction, Integer>, TransactionCustomRepository {
//    @Query(value = "SELECT b FROM Transaction b where b.enable=1 AND b.orderId=?1")
//    Transaction findByOrderId(String orderId);
//
//    @Query(value = "SELECT count(t.id) FROM `transaction` t " , nativeQuery = true)
//    Integer findTransactionCount();
//
//    @Query(value = "SELECT count(t.id) FROM `transaction` t  where FIND_IN_SET(operating_unit_id, :operatingUnitIds) > 0 " , nativeQuery = true)
//    Integer findTransactionCountByOperatingUnits(String operatingUnitIds);
//
//    @Query("SELECT t FROM Transaction t ORDER BY t.id DESC")
//    Page<Transaction> findAll(Pageable pageable);
//
//
////    @Query(value = "SELECT * FROM `transaction` t WHERE FIND_IN_SET(t.operating_unit_id, :operatingUnitIds) > 0", nativeQuery = true)
////    List<Transaction> findAllByOperatingUnitIds(@Param("operatingUnitIds") String operatingUnitIds);
//
//}
