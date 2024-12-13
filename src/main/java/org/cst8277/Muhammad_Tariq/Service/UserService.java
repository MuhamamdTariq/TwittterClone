package org.cst8277.Muhammad_Tariq.Service;

import java.util.UUID;

import org.cst8277.Muhammad_Tariq.Entity.Role;
import org.cst8277.Muhammad_Tariq.Entity.RoleType;
import org.cst8277.Muhammad_Tariq.Entity.Subscription;
import org.cst8277.Muhammad_Tariq.Entity.Token;
import org.cst8277.Muhammad_Tariq.Entity.Users;
import org.cst8277.Muhammad_Tariq.Repository.RoleRepo;
import org.cst8277.Muhammad_Tariq.Repository.SubscriptionRepository;
import org.cst8277.Muhammad_Tariq.Repository.TokenRepository;
import org.cst8277.Muhammad_Tariq.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@SuppressWarnings("unused")
@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepo roleRepo;
	
	
	public boolean userExists(String username) {
		return userRepo.findByUsername(username) != null;
	}
	
	public Users registerUser(Users user) {
		if (user.getRole() == null) {
            throw new RuntimeException("User role cannot be null");
        }

        // Check if the user's role is either PRODUCER or SUBSCRIBER
        if (!user.getRole().getName().equals(RoleType.PRODUCER) &&
            !user.getRole().getName().equals(RoleType.SUBSCRIBER)) {
            throw new RuntimeException("User role must be either PRODUCER or SUBSCRIBER");
        }
        Role savedRole = roleRepo.save(user.getRole());
        user.setRole(user.getRole());
        // Encode the user's password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        return userRepo.save(user);
    }
	
	public Users validateCredentials(String username, String password) {
	    // Retrieve the user by username
	    Users user = userRepo.findByUsername(username);
	    
	    // Check if the user exists
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found with username: " + username);
	    }

	    // Validate the password (assuming passwords are stored hashed)
	    if (!passwordEncoder.matches(password, user.getPassword())) {
	        throw new BadCredentialsException("Invalid password for username: " + username);
	    }

	    // Return the user if validation is successful
	    return user;
	}

	
	
	

}