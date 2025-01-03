package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }

    public void saveNewUser (User user) {
        userRepository.save(user);
    }

    public Optional<User> getById(String myId) {
        return userRepository.findById(myId);
    }

    public void deleteById(String myId) {
        userRepository.deleteById(myId);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // New method to generate password reset token
    public String generatePasswordResetToken(String username) {
        User user = findByUsername(username);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiration(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
            saveNewUser (user);
            return token;
        }
        return null;
    }

    // New method to validate the reset token
    public boolean validateResetToken(String token) {
        User user = userRepository.findByResetToken(token);
        return user != null && user.getResetTokenExpiration().isAfter(LocalDateTime.now());
    }

    // New method to reset the password
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null); // Clear the token
            user.setResetTokenExpiration(null); // Clear the expiration
            saveNewUser (user);
        }
    }
}