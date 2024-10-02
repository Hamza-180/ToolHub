package be.ehb.toolhub.repository;

import be.ehb.toolhub.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Methode om producten op basis van categorie te vinden
    List<Product> findByCategory(String category);

    // Query om unieke categorieën op te halen
    @Query("SELECT DISTINCT p.category FROM Product p")
    Set<String> findAllCategories();
}
