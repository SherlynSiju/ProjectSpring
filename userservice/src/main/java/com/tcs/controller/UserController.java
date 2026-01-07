package com.tcs.controller;

import com.tcs.dto.AuthRequest;
import com.tcs.dto.AuthResponse;
import com.tcs.entity.Users;
import com.tcs.service.UserService;
import com.tcs.util.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ================= AUTH =================

    // -------- LOGIN (PUBLIC) --------
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // -------- REGISTER (PUBLIC) --------
    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users users) {
        return ResponseEntity.ok(userService.registerUser(users));
    }

    // ================= USER =================

    // -------- GET USER BY ID (JWT REQUIRED) (USER/ADMIN)--------
    @GetMapping("/user/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // -------- UPDATE USER (USER/ADMIN) --------
    @PutMapping("/user/{id}")
    public ResponseEntity<Users> updateUser(
            @PathVariable Long id,
            @RequestBody Users users) {

        return ResponseEntity.ok(userService.updateUserById(id, users));
    }

    // ================= ADMIN =================

    // -------- SOFT DELETE (ADMIN ONLY) --------
    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
    
    // -------- GET ALL USERS (ADMIN ONLY) --------
    @GetMapping("/admin/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ================= TEST =================

    // -------- JWT TEST --------
    @GetMapping("/user/test")
    public ResponseEntity<String> testJwt() {
        return ResponseEntity.ok("JWT is valid and working");
    }
}