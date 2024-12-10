package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User user){
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }

    public Optional<User> getById(String myId){
        return userRepository.findById(myId);
    }

    public void deleteById(String myId){
        userRepository.deleteById(myId);
    }

    // Find a user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
