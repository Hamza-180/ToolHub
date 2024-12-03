package be.ehb.toolhub.service;

import be.ehb.toolhub.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user); // Als deze niet wordt gebruikt, kun je deze verwijderen
    void deleteUser(Long id);
    User registerUser(User user); // Gebruiker registreren
   
}
