package com.cueballdb.repository;

import com.cueballdb.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>, BookingCustomRepository{
    Booking findByTitle(String title);

    // For retrieving the conflicts of Booking
    @Query("SELECT b FROM Booking b WHERE " +
            "b.roomId = :roomId AND " +
            "b.timeOut > CURRENT_TIMESTAMP AND " +
            "b.id <> :bookingId AND (" +  // <-- Exclude current record
            "(:newTimeIn BETWEEN b.timeIn AND b.timeOut) OR " +
            "(:newTimeOut BETWEEN b.timeIn AND b.timeOut) OR " +
            "(b.timeIn BETWEEN :newTimeIn AND :newTimeOut))")
    List<Booking> findConflictBookings(@Param("bookingId") int bookingId ,@Param("newTimeIn") Date newTimeIn, @Param("newTimeOut") Date newTimeOut, @Param("roomId") int roomId);

//    @Query("SELECT COUNT(*) FROM booking b " +
//            "RIGHT JOIN room r ON r.id = b.room_id " +
//            "AND NOT " +
//            "( " +
//            " GREATEST(b.time_out, b.check_out) <= CURRENT_TIMESTAMP " +
//            "OR " +
//            "LEAST(b.time_in, b.check_in) >= CURRENT_TIMESTAMP " +
//            ") " +
//            "WHERE b.room_id IS NULL")
//    long findAvailableRoomsCount();
}

