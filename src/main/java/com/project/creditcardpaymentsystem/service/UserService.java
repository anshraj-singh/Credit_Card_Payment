package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }
    public void saveNewUser(User user) {
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
}