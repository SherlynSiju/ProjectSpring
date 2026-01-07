package com.tcs.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.entity.Users;
import com.tcs.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

//  ------------ REGISTER -----------------
    @Override
    public Users registerUser(Users users) {
        if (userRepo.findByUsernameAndIsDeletedFalse(users.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        if(users.getRole()==null || users.getRole()=="")
        	users.setRole("USER");
        users.setIsDeleted(false);

        return userRepo.save(users);
    }

    
//  ----------------- GET BY ID -------------------
	@Override
	public Users getUserById(Long id) {
		Users user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getIsDeleted()) {
            throw new RuntimeException("User is deleted");
        }

        return user;
	}

	// ---------------- UPDATE ----------------
    @Override
    public Users updateUserById(Long id, Users updatedUser) {

        Users existingUser = getUserById(id);

        // Logged-in user (from JWT)
        String loggedInUsername = getLoggedInUsername();

        // Only self or ADMIN can update
        if (!existingUser.getUsername().equals(loggedInUsername)
                && !isAdmin()) {
            throw new RuntimeException("Unauthorized to update this user");
        }

        existingUser.setUsername(updatedUser.getUsername());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(
                    passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepo.save(existingUser);
    }


// 	---------------- GET ALL USERS (ADMIN) ----------------
    @Override
    public List<Users> getAllUsers() {

        if (!isAdmin()) {
            throw new RuntimeException("Only ADMIN can view all users");
        }

        return userRepo.findAll();
    }
    
    
// 	---------------- DELETE (SOFT DELETE) ----------------
    @Override
    public String deleteUserById(Long id) {

        Users user = getUserById(id);

        if (!isAdmin()) {
            throw new RuntimeException("Only ADMIN can delete users");
        }

        user.setIsDeleted(true);
        userRepo.save(user);

        return "User deleted successfully";
    }

// 	---------------- PASSWORD VERIFY ----------------
	@Override
	public boolean verifyPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	
// 	================= HELPER METHODS =================

    private String getLoggedInUsername() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private boolean isAdmin() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        
        auth.getAuthorities().forEach(a ->
        System.out.println("Authority: " + a.getAuthority()));

        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    
    
}