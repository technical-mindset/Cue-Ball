//package com.cueballdb.repository;
//
//import com.cueballdb.model.Bank;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
////@Repository
//public interface BankRepository extends JpaRepository<Bank, Integer>, BankCustomRepository {
//    List<Bank> findAllByEnableTrue();
//
//    @Query(value = "SELECT count(b.id) FROM bank b WHERE b.enable = 1", nativeQuery = true)
//    Integer findAllByEnableTrueCount();
//
//    Bank findById(int id);
//
//    @Query(value = "SELECT count(b.id) FROM `bank` b " , nativeQuery = true)
//    Integer findBankCount();
//
//    @Query(value = "SELECT * FROM bank b WHERE FIND_IN_SET(b.id, :ids) > 0", nativeQuery = true)
//    List<Bank> findBanksByIds(@Param("ids") String ids);
//
//    @Query(value = "SELECT * FROM bank b WHERE FIND_IN_SET(b.id, :bankIds) > 0 AND FIND_IN_SET(:currencyId, b.currency_id) > 0", nativeQuery = true)
//    List<Bank> findBanksByCurrencyIds(@Param("bankIds") String bankIds, @Param("currencyId") Integer currencyId);
//
//    Bank findByName(String name);
//
//}
