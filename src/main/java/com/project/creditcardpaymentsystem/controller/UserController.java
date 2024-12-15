package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.service.CustomerService;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;


    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<User> allUser = userService.findAllUsers();
        if(allUser != null && !allUser.isEmpty()){
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> createNewAccount(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable String myId){
        Optional<User> myEntry = userService.getById(myId);
        if(myEntry.isPresent()){
            return new ResponseEntity<>(myEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteUserById(@PathVariable String myId){
        userService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update a user by ID
    @PutMapping("/id/{myId}")
    public ResponseEntity<User> updateEntryById(@PathVariable String myId, @RequestBody User newEntry) {
        User existingUser = userService.getById(myId).orElse(null);
        if (existingUser != null) {
            if (newEntry.getUsername() != null && !newEntry.getUsername().isEmpty()) {
                existingUser.setUsername(newEntry.getUsername());
            }

            if (newEntry.getPassword() != null && !newEntry.getPassword().isEmpty()) {
                existingUser.setPassword(newEntry.getPassword());
            }

            if (newEntry.getRoles() != null && !newEntry.getRoles().isEmpty()) {
                existingUser.setRoles(newEntry.getRoles());
            }

            if (newEntry.getCustomerId() != null) {
                existingUser.setCustomerId(newEntry.getCustomerId());
            }

            userService.saveUser(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
