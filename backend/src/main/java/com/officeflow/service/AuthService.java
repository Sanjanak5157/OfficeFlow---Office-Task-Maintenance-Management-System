package com.officeflow.service;

import com.officeflow.entity.User;
import java.util.List;

/**
 * Basic Spring Boot Service for User Authentication and Directory.
 */
public interface AuthService {
    User login(String email, String password);
    List<User> getAllUsers();
    User getUserById(Long id);
}
