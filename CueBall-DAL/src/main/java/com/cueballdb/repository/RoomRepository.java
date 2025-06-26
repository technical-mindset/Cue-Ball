package com.cueballdb.repository;

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

    @Query("SELECT r FROM Room r WHERE r.enable = true AND r.delete = false ORDER BY r.id DESC")
    List<Room> findAllByEnableTrueAndDeleteFalse();

    @Query("SELECT r FROM Room r WHERE r.roomCategory.id = :roomCategoryId AND r.enable = true")
    List<Room> findAllByRoomCategory(@Param("roomCategoryId") Integer roomCategoryId);

    @Query("SELECT COUNT(*) FROM Room r where r.delete = false")
    long count();

    @Query(value = "SELECT COUNT(*) FROM room r " +
            "LEFT JOIN booking b ON r.id = b.room_id " +
            "AND NOT ( " +
            " GREATEST(COALESCE(b.time_out, '1970-01-01'), COALESCE(b.check_out, '1970-01-01')) <= CURRENT_TIMESTAMP " +
            " OR " +
            " LEAST(COALESCE(b.time_in, '9999-12-31'), COALESCE(b.check_in, '9999-12-31')) >= CURRENT_TIMESTAMP " +
            ") " +
            "WHERE b.room_id IS NULL AND r.delete = false", nativeQuery = true)
    long findAvailableRoomsCount();



    @Query(value = "SELECT r.* FROM room r " +
            "LEFT JOIN booking b ON r.id = b.room_id " +
            "AND NOT ( " +
            " GREATEST(COALESCE(b.time_out, '1970-01-01'), COALESCE(b.check_out, '1970-01-01')) <= CURRENT_TIMESTAMP " +
            " OR " +
            " LEAST(COALESCE(b.time_in, '9999-12-31'), COALESCE(b.check_in, '9999-12-31')) >= CURRENT_TIMESTAMP " +
            ") " +
            "WHERE b.room_id IS NULL AND r.room_category_id = :roomCategoryId AND r.enable = true AND r.delete = false ", nativeQuery = true)
    List<Room> findAvailableRoomsByCategory(@Param("roomCategoryId") Integer roomCategoryId);



    @Query(value = "SELECT r.*, b.time_in, b.time_out FROM room r " +
            "LEFT JOIN booking b ON r.id = b.room_id " +
            "AND NOT ( " +
            " GREATEST(COALESCE(b.time_out, '1970-01-01'), COALESCE(b.check_out, '1970-01-01')) <= CURRENT_TIMESTAMP " +
            " OR " +
            " LEAST(COALESCE(b.time_in, '9999-12-31'), COALESCE(b.check_in, '9999-12-31')) >= CURRENT_TIMESTAMP " +
            ") " +
            "WHERE b.room_id IS NOT NULL AND r.room_category_id = :roomCategoryId", nativeQuery = true)
    List<Room> findNotAvailableRoomsByCategory(@Param("roomCategoryId") Integer roomCategoryId);

}

