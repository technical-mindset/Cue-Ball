package com.cueballdb.repository;

import com.cueballdb.model.Game;
import com.cueballdb.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, RoomCustomRepository{
    Room findByTitleAndName(String title, String name);

    long count();

}

