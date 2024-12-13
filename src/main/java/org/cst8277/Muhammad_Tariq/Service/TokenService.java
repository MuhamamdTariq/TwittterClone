package org.cst8277.Muhammad_Tariq.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.cst8277.Muhammad_Tariq.Entity.Token;
import org.cst8277.Muhammad_Tariq.Entity.Users;
import org.cst8277.Muhammad_Tariq.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
	@Autowired
	private TokenRepository tokenRepo;
	
	


	public String generateToken(Users user) {		
		Optional<Token> optionalToken = tokenRepo.findByUserId(user.getId());

	    if (optionalToken.isPresent()) {
	        Token existingToken = optionalToken.get();

	        // Check if the token is expired
	        if (existingToken.getExpirationTime().isAfter(LocalDateTime.now())) {
	            // Token is valid
	            return "token already exists";
	        } else {
	            // Token is expired, revoke or delete the token
	            tokenRepo.delete(existingToken);
	        }
	    }
		UUID tokenUUID = UUID.randomUUID();
		String tokenString = tokenUUID.toString();
		LocalDateTime expirationNow = LocalDateTime.now().plusHours(1);
		Token token = new Token();
		token.setToken(tokenString);
		token.setExpirationTime(expirationNow);
		token.setUser(user);
		tokenRepo.save(token);
		
		return tokenString;
	}
	
	public boolean isTokenValid(String token) {
        Token storedToken = tokenRepo.findByToken(token);
        if (storedToken != null && storedToken.getExpirationTime().isAfter(LocalDateTime.now())) {
            return true; // Token is valid and not expired
        }
        return false; // Token is invalid or expired
    }
}