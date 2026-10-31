package com.officeflow.service.impl;

import com.officeflow.entity.User;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String email, String password) {
        // String methods: trim & lowercase
        String cleanEmail = (email != null) ? email.trim().toLowerCase() : "";
        return userRepository.findByEmail(cleanEmail)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
