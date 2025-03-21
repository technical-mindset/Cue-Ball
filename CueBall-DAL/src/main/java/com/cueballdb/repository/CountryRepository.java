//package com.cueballdb.repository;
//
//import com.cueballdb.model.Country;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
////@Repository
//public interface CountryRepository extends JpaRepository<Country, Integer> {
//
//    @Query("SELECT c FROM Country c ORDER BY c.id ASC")
//    List<Country> findAll();
//
//}
