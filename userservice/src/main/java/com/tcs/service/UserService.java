package com.tcs.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.tcs.entity.Users;

public interface UserService {

    // Register a new user
    Users registerUser(Users users);

    // Get user by ID
    Users getUserById(Long id);

    // Update user by ID
    Users updateUserById(Long id, Users users);

    // Get all users
    List<Users> getAllUsers();

    // Delete user by ID
    String deleteUserById(Long id);

    // Verify password
    boolean verifyPassword(String rawPassword, String encodedPassword);
}