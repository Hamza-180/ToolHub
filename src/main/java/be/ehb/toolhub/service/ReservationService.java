package be.ehb.toolhub.service;

import be.ehb.toolhub.model.Reservation;
import be.ehb.toolhub.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Accepteert nu een Reservation-object in plaats van een String
    public Reservation createReservation(Reservation reservation) {
        // Hier kun je andere logica toevoegen indien nodig
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationsByUsername(String username) {
        return reservationRepository.findByUsername(username);
    }
}
