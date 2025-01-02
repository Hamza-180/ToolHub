package be.ehb.toolhub.controller;

import be.ehb.toolhub.model.Reservation;
import be.ehb.toolhub.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
public ResponseEntity<Reservation> createReservation(
        @RequestBody Reservation reservation,
        @AuthenticationPrincipal UserDetails userDetails) {

    // Validate required fields
    if (userDetails == null || userDetails.getUsername() == null) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    if (reservation.getEmail() == null || reservation.getEmail().trim().isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    reservation.setUsername(userDetails.getUsername());

    try {
        Reservation createdReservation = reservationService.createReservation(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
