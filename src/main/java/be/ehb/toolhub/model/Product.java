package be.ehb.toolhub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private String category;



    private int quantity;

    @NotNull
    private BigDecimal price;

    public Product() {

    }

    public Product(String name, String description, String category, int quantity, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }



    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
