package com.cueballdb.repository;

import com.cueballdb.model.TucBuying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TuckBuyingRepository extends JpaRepository<TucBuying, Integer>,TuckBuyingCustomRepository{ }

