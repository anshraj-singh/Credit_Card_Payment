package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get the currently authenticated user
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("User  not authenticated", HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName(); // Get the username of the logged-in user
        User user = userService.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("User  not found", HttpStatus.NOT_FOUND);
    }

    // Create a new user
    @PostMapping("/create-user")
    public ResponseEntity<?> createNewAccount(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get a user by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Optionally, you can add checks to ensure that only certain users can access this
        Optional<User> myEntry = userService.getById(myId);
        if (myEntry.isPresent()) {
            return new ResponseEntity<>(myEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a user by ID
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteUserById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user has admin role (you can customize this check)
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
            userService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    // Update a user by ID
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntryById(@PathVariable String myId, @RequestBody User newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user has admin role (you can customize this check)
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
            User existingUser  = userService.getById(myId).orElse(null);
            if (existingUser  != null) {
                if (newEntry.getUsername() != null && !newEntry.getUsername().isEmpty()) {
                    existingUser .setUsername(newEntry.getUsername());
                }

                if (newEntry.getPassword() != null && !newEntry.getPassword().isEmpty()) {
                    existingUser .setPassword(newEntry.getPassword());
                }

                if (newEntry.getRoles() != null && !newEntry.getRoles().isEmpty()) {
                    existingUser .setRoles(newEntry.getRoles());
                }

                if (newEntry.getCustomerId() != null) {
                    existingUser .setCustomerId(newEntry.getCustomerId()); // Fixed: Added parentheses
                }

                userService.saveUser (existingUser );
                return new ResponseEntity<>(existingUser , HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }
}