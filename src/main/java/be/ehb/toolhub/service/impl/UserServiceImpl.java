package be.ehb.toolhub.service.impl;

import be.ehb.toolhub.model.User;
import be.ehb.toolhub.repository.UserRepository;
import be.ehb.toolhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Wachtwoord hashen
        return userRepository.save(user); // Sla de gebruiker op in de database
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

   @Override
public User registerUser(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
        throw new IllegalArgumentException("Gebruikersnaam bestaat al");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword())); // Wachtwoord hashen
    return userRepository.save(user);
}


}
