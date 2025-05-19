package com.cueballdb.repository;

import com.cueballdb.model.Game;
import com.cueballdb.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, RoomCustomRepository{
    Room findByTitleAndName(String title, String name);

    List<Room> findAllByEnableTrue();

    @Query("SELECT r FROM Room r WHERE r.roomCategory.id = :roomCategoryId")
    List<Room> findAllByRoomCategory(@Param("roomCategoryId") Integer roomCategoryId);


    long count();

    @Query(value = "SELECT COUNT(*) FROM room r " +
            "LEFT JOIN booking b ON r.id = b.room_id " +
            "AND NOT " +
            "( " +
            " GREATEST(b.time_out, b.check_out) <= CURRENT_TIMESTAMP " +
            "OR " +
            "LEAST(b.time_in, b.check_in) >= CURRENT_TIMESTAMP " +
            ") " +
            "WHERE b.room_id IS NULL", nativeQuery = true)
    long findAvailableRoomsCount();


    @Query(value = "SELECT r.* FROM room r " +
            "LEFT JOIN booking b ON r.id = b.room_id " +
            "AND NOT " +
            "( " +
            " GREATEST(b.time_out, b.check_out) <= CURRENT_TIMESTAMP " +
            "OR " +
            "LEAST(b.time_in, b.check_in) >= CURRENT_TIMESTAMP " +
            ") " +
            "WHERE b.room_id IS NULL AND r.room_category_id = :roomCategoryId", nativeQuery = true)
    List<Room> findAvailableRoomsByCategory(@Param("roomCategoryId") Integer roomCategoryId);


    @Query(value = "SELECT r.* FROM room r " +
            "LEFT JOIN booking b ON r.id = b.room_id " +
            "AND NOT " +
            "( " +
            " GREATEST(b.time_out, b.check_out) <= CURRENT_TIMESTAMP " +
            "OR " +
            "LEAST(b.time_in, b.check_in) >= CURRENT_TIMESTAMP " +
            ") " +
            "WHERE b.room_id IS NOT NULL AND r.room_category_id = :roomCategoryId", nativeQuery = true)
    List<Room> findNotAvailableRoomsByCategory(@Param("roomCategoryId") Integer roomCategoryId);

}

