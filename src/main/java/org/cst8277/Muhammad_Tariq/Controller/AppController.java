package org.cst8277.Muhammad_Tariq.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.cst8277.Muhammad_Tariq.Dto.MessageDto;
import org.cst8277.Muhammad_Tariq.Dto.SubscribeDto;
import org.cst8277.Muhammad_Tariq.Dto.loginDto;
import org.cst8277.Muhammad_Tariq.Entity.Message;
import org.cst8277.Muhammad_Tariq.Entity.Subscription;
import org.cst8277.Muhammad_Tariq.Entity.Users;
import org.cst8277.Muhammad_Tariq.Repository.MessageRepository;
import org.cst8277.Muhammad_Tariq.Repository.SubscriptionRepository;
import org.cst8277.Muhammad_Tariq.Repository.UserRepository;
import org.cst8277.Muhammad_Tariq.Service.MessageService;
import org.cst8277.Muhammad_Tariq.Service.SubscriptionService;
import org.cst8277.Muhammad_Tariq.Service.TokenService;
import org.cst8277.Muhammad_Tariq.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController 
@RequestMapping("/api")
public class AppController {
	private final UserRepository UserRepo;
	private final MessageService messageService;
	private final SubscriptionRepository subscriptionRepo;
	private final UserService userService;
	private final TokenService tokenService;
	private final SubscriptionService subscriptionService;
	private final MessageRepository messageRepo;
	
	public AppController(UserRepository userRepo, MessageService messageService, SubscriptionRepository subscriptionRepo,
			UserService userService, TokenService tokenService, SubscriptionService subscriptionService, MessageRepository messageRepo) {
		this.UserRepo = userRepo;
		this.messageService = messageService;
		this.subscriptionRepo = subscriptionRepo;
		this.userService = userService;
		this.tokenService = tokenService;
		this.subscriptionService = subscriptionService;
		this.messageRepo = messageRepo;
		
	}
	

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody Users user) {
	    try {
	        // Check if the user already exists
	        if (userService.userExists(user.getUsername())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                    .body("A user with this username already exists.");
	        }

	        // Register the new user
	        Users newUser = userService.registerUser(user);

	        // Generate a token for the newly registered user
	        String token = tokenService.generateToken(newUser);

	        // Return the user details (excluding sensitive information) along with the token
	        Map<String, Object> response = new HashMap<>();
	        response.put("user", Map.of(
	                "id", newUser.getId(),
	                "username", newUser.getUsername(),
	                "email", newUser.getEmail() // Include only non-sensitive details
	        ));
	        response.put("token", token);

	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An unexpected error occurred while registering the user.");
	    }
	}


	    @PostMapping("/login")
	    public ResponseEntity<?> logUserIn(@RequestBody loginDto loginDto) {
	        try {
	            // Validate user credentials
	            Users user = userService.validateCredentials(loginDto.getUsername(), loginDto.getPassword());
	            if (user == null) {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	            }

	            
	            String token = tokenService.generateToken(user);

	            // Return the user along with the token
	            return ResponseEntity.ok(Map.of(
	                "user", user,
	                "token", token
	            ));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("An error occurred while logging in");
	        }
	    }
	

	
	@GetMapping("/users")
	public List<Users> getAllUsers(){
		return UserRepo.findAll();
	}
	
	@PostMapping("/send_message")
	public ResponseEntity<?> postMessage(@RequestBody MessageDto messagedto) {
		try {
	        Message savedMessage = messageService.saveMessage(messagedto.getContent(), messagedto.getProducerId());
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry you may not be a producer");
	    }
		
	}
	
	@DeleteMapping("/delete_message/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId) {
        try {
            messageRepo.deleteById(messageId);
            return ResponseEntity.status(HttpStatus.OK).body("Message deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting message");
        }
	}
	
	 @GetMapping("/users/messages/{userId}")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable String userId) {
        List<Message> messages = messageService.getMessagesByUser(userId);
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }
	 
	 @GetMapping("/users/messages")
	 public ResponseEntity<List<Message>> getAllMessages(){
		 List<Message> messages = messageService.returnAll();
		 if(messages.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(messages);
		 
	 }
	
	@PostMapping("/subscribe")
	public String subscribe(@RequestBody SubscribeDto subscribeDTO){
		return subscriptionService.subscribe(subscribeDTO.getSubscriber_Id(), subscribeDTO.getProducer_Id());
		
		
	}
	
	
	@PostMapping("/subscriber/messages/{subscriberId}")
	public Optional<Message> getSubscriptions(@PathVariable String subscriberId){
		return messageRepo.findById(subscriberId);
	}
	
	 @PutMapping("/update/{messageId}")
	    public ResponseEntity<Message> updateMessage(@PathVariable String messageId, @RequestBody String Content) {
	        try {
	            // Retrieve the existing message
	            Message existingMessage = messageRepo.findById(messageId)
	                    .orElseThrow(() -> new IllegalArgumentException("Message with id " + messageId + " not found"));

	            // Update the message with the new data
	            existingMessage.setContent(Content);

	            // Save the updated message
	            Message updatedMessage = messageRepo.save(existingMessage);

	            return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

}