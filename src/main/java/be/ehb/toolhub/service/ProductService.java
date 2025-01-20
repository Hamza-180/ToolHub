package be.ehb.toolhub.service;

import be.ehb.toolhub.model.Product;
import be.ehb.toolhub.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Haalt een lijst op van alle producten uit de database.
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//Zoekt en retourneert producten die behoren tot een specifieke categorie
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Methode om alle unieke categorieën op te halen
    public Set<String> getAllCategories() {
        return productRepository.findAll().stream()
                .map(Product::getCategory) // Zorg ervoor dat je een getCategory() methode hebt in het Product model
                .collect(Collectors.toSet());
    }
}
