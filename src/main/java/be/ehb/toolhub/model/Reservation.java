package be.ehb.toolhub.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user; //

    @ManyToOne
    private Product product;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public Reservation() {

    }

    public Reservation(User user, Product product, LocalDate startDate, LocalDate endDate, String status) {
        this.user = user;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }



    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
