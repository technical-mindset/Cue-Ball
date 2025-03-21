//package com.cueballdb.repository;
//
//import com.cueballdb.model.OperatingUnit;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface OperatingUnitRepository extends JpaRepository<OperatingUnit, Integer>, OperatingUnitCustomRepository {
//    OperatingUnit findByClientId(String clientId);
//
//    Optional<OperatingUnit> findByUsername(String username);
//
//    List<OperatingUnit> findAllByEnableTrue();
//
//    OperatingUnit findById(int id);
//
//    @Query(value = "SELECT count(o.id) FROM `operating_unit` o " , nativeQuery = true)
//    Integer findOperatingUnitCount();
//
//    @Query(value = "SELECT count(o.id) FROM `operating_unit` AS o WHERE FIND_IN_SET(o.id, :operatingUnitIds) > 0" , nativeQuery = true)
//    Integer findOperatingUnitCountByOperatingUnit(@Param("operatingUnitIds") String operatingUnitIds);
//
//
//    @Query(value = "SELECT * FROM `operating_unit` AS op WHERE FIND_IN_SET(op.id, :operatingUnitIds) > 0", nativeQuery = true)
//    List<OperatingUnit> findAllByIds(@Param("operatingUnitIds") String operatingUnitIds);
//
//
//    @Query(value = "SELECT count(op.id) FROM `operating_unit` AS op WHERE FIND_IN_SET(:bankId, op.bank_id) > 0", nativeQuery = true)
//    Integer findByBankContains(@Param("bankId") int bankId);
//
//
//    @Query(value = "SELECT count(op.id) FROM `operating_unit` AS op JOIN `ou_currency_bridge` AS ouc ON op.id = ouc.operating_unit_id " +
//            "WHERE ouc.currency_id = :currencyId", nativeQuery = true)
//    Integer findByCurrencyContains(@Param("currencyId") int currencyId);
//
//}
