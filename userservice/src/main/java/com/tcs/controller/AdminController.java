//package com.tcs.controller;
//
//import com.tcs.entity.Users;
//import com.tcs.exception.UserNotFoundException;
//import com.tcs.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin")
//public class AdminController {
//
//    @Autowired
//    private UserService userService;
//
//    // Register a new staff (Admin only)
//    @PostMapping("/staff/register")
//    public Users registerStaff(@RequestBody Users users) {
//        return userService.registerUser(users);
//    }
//
//    // Update staff details (Admin only)
//    @PutMapping("/staff/{id}")
//    public Users updateStaff(@PathVariable Long id, @RequestBody Users users) throws UserNotFoundException {
//        return userService.updateUserById(id, users);
//    }
//
//    // Delete staff (Admin only)
//    @DeleteMapping("/staff/{id}")
//    public String deleteStaff(@PathVariable Long id) throws UserNotFoundException {
//        return userService.deleteUserById(id);
//    }
//
//    // Get all staff (Admin only)
//    @GetMapping("/staff")
//    public List<Users> getAllStaff() {
//        return userService.getAllUser();
//    }
//}