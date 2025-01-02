package be.ehb.toolhub.controller;

import be.ehb.toolhub.model.Product;
import be.ehb.toolhub.model.Reservation;
import be.ehb.toolhub.model.User;
import be.ehb.toolhub.repository.ReservationRepository;
import be.ehb.toolhub.repository.UserRepository; // Voeg deze import toe
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private List<Product> cart = new ArrayList<>();

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository; // Voeg deze lijn toe

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
public ResponseEntity<String> checkout(@RequestBody User user) {
    if (cart.isEmpty()) {
        return new ResponseEntity<>("Cart is empty", HttpStatus.BAD_REQUEST);
    }

    // Validate user data
    if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
        return new ResponseEntity<>("Username is required", HttpStatus.BAD_REQUEST);
    }
    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
        return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
    }

    for (Product product : cart) {
        try {
            Reservation reservation = new Reservation();
            reservation.setUsername(user.getUsername().trim());
            reservation.setEmail(user.getEmail().trim());
            reservation.setProduct(product);
            reservation.setStartDate(LocalDate.now());
            reservation.setEndDate(LocalDate.now().plusDays(1));
            reservation.setStatus("Bevestigd");

            reservationRepository.save(reservation);
        } catch (Exception e) {
            return new ResponseEntity<>("Error during checkout: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    cart.clear();
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
