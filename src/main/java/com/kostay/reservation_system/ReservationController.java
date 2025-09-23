package com.kostay.reservation_system;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        logger.info("Called method: getReservationById: id={}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        logger.info("Called method: getAllReservations");
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid Reservation reservationToCreate) {
        logger.info("Called method createReservation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("test-header", "test-header-value")
                .body(reservationService.createReservation(reservationToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") Long id,
            @RequestBody @Valid Reservation reservationToUpdate) {
        logger.info("Called method updateReservation: id={}, reservationToUpdate={}", id, reservationToUpdate);
        var updated = reservationService.updateReservation(id, reservationToUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        logger.info("Called method deleteReservation: id={}", id);
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Reservation> approveReservation(@PathVariable("id") Long id) {
        logger.info("Called method approveReservation: id={}", id);
        var reservation = reservationService.approveReservation(id);
        return ResponseEntity.ok(reservation);
    }
}
