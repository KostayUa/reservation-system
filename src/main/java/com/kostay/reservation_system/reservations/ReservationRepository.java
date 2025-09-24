package com.kostay.reservation_system.reservations;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

//    List<ReservationEntity> findAllByStatusIs(ReservationStatus status);

//    @Query("select r from ReservationEntity r where r.status = :status")
//    List<ReservationEntity> findAllByStatusIs(@Param("status") ReservationStatus status);

//    @Query(value = "select * from reservations r where r.status = :status", nativeQuery = true)
//    List<ReservationEntity> findAllByStatusIs(@Param("status") ReservationStatus status);

//    @Query("select r from ReservationEntity r where r.roomId = :roomId")
//    List<ReservationEntity> findAllByRoomId(@Param("roomId") Long roomId);

//    @Transactional
//    @Modifying
//    @Query("""
//            update ReservationEntity r
//            set r.userId = :userId,
//                r.roomId = :roomId,
//                r.startDate = :startDate,
//                r.endDate = :endDate,
//                r.status = :status
//            where r.id = :id
//            """)
//    int updateAllFilds(
//            @Param("id") Long id,
//            @Param("userId") Long userId,
//            @Param("roomId") Long roomId,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate,
//            @Param("status") ReservationStatus status
//    );

    @Modifying
    @Query("""
            UPDATE ReservationEntity r
                SET r.status = :status
                WHERE r.id = :id
            """)
    void setStatus(@Param("id") Long id, @Param("status") ReservationStatus reservationStatus);

    @Query("""
            SELECT r.id FROM ReservationEntity r
                WHERE r.roomId = :roomId
                AND :startDate < r.endDate
                AND r.startDate < :endDate
                AND r.status = :status
            """)
    List<Long> findConflictRoomIds(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") ReservationStatus status
    );

    @Query("""
            SELECT r FROM ReservationEntity r
                WHERE (:userId IS NULL OR r.userId = :userId)
                AND (:roomId IS NULL OR r.roomId = :roomId)
            """)
    List<ReservationEntity> searchByFilter(
            @Param("userId") Long userId,
            @Param("roomId") Long roomId,
            Pageable pageable
    );
}
