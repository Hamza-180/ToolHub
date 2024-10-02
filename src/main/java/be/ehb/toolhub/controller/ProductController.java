package be.ehb.toolhub.controller;

import be.ehb.toolhub.model.Product;
import be.ehb.toolhub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors; // Voor gebruik in getAllCategories

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return productService.getProductsByCategory(category);
        }
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.saveProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Voeg deze methode toe om alle unieke categorieën op te halen
    @GetMapping("/categories")
    public ResponseEntity<Set<String>> getAllCategories() {
        Set<String> categories = (Set<String>) productService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
