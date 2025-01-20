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

    //Haalt een lijst op van alle reserveringen uit de database.
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public Reservation createReservation(Reservation reservation) {

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // Haalt een lijst op van reserveringen die gekoppeld zijn aan een specifieke gebruiker
    public List<Reservation> getReservationsByUsername(String username) {
        return reservationRepository.findByUsername(username);
    }
}
