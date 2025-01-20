package be.ehb.toolhub.repository;

import be.ehb.toolhub.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository

//Zoekt reserveringen op basis van de gebruikersnaam.
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUsername(String username);
}
