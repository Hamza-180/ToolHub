package be.ehb.toolhub.controller;

import be.ehb.toolhub.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private List<Product> cart = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Product>> getCart() {
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody Product product) {
        cart.add(product);
        return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
        cart.removeIf(product -> product.getId().equals(id));
        return new ResponseEntity<>("Product removed from cart", HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout() {
        // Hier kun je de logica toevoegen voor de checkout, zoals het opslaan van de bestelling in de database
        cart.clear(); // Leeg de winkelwagentje na de checkout
        return new ResponseEntity<>("Checkout completed", HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalPrice() {
        BigDecimal total = cart.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}
