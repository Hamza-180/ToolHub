package be.ehb.toolhub.controller;

import be.ehb.toolhub.model.User;
import be.ehb.toolhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return new ResponseEntity<>("Login succesvol", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Ongeldige gebruikersnaam of wachtwoord", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Inloggen mislukt: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>("Registratie succesvol", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registratie mislukt: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
