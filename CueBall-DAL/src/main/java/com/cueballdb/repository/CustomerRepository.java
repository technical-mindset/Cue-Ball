package com.cueballdb.repository;

import com.cueballdb.model.Booking;
import com.cueballdb.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

