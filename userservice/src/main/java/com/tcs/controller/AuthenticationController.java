//package com.tcs.controller;
//
//import com.tcs.dto.AuthRequest;
//import com.tcs.dto.AuthResponse;
//import com.tcs.service.UserService;
//import com.tcs.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AuthenticationController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/api/login")
//    public AuthResponse createJwtToken(@RequestBody AuthRequest authRequest) throws Exception {
//        // Authenticate the user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//        );
//
//        // Retrieve the user details from the authentication object
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        // Generate the JWT token
//        String token = jwtUtil.generateToken(userDetails.getUsername());
//
//        // Return the token in the response
//        return new AuthResponse(token);
//    }
//}