//package com.cueballdb.repository;
//
//import com.cueballdb.model.Currency;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
////@Repository
//public interface CurrencyRepository extends JpaRepository<Currency, Integer>, CurrencyCustomRepository {
//
//    Currency findByCurrencyCode(String currencyCode);
//
//    List<Currency> findAllByEnableTrue();
//
//    @Query("SELECT c FROM Currency c ORDER BY c.id DESC")
//    Page<Currency> findAll(Pageable pageable);
//
//    @Query(value = "SELECT count(c.id) FROM `currency` c " , nativeQuery = true)
//    Integer findCurrencyCount();
//
//    @Query(value = "SELECT * FROM `currency` AS c WHERE FIND_IN_SET(c.id, :currencyIds) > 0", nativeQuery = true)
//    List<Currency> findCurrenciesByIds(@Param("currencyIds") String currencyIds);
//
//}
