package org.cst8277.Muhammad_Tariq.Service;

import org.cst8277.Muhammad_Tariq.Entity.Role;
import org.cst8277.Muhammad_Tariq.Entity.RoleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.cst8277.Muhammad_Tariq.Entity.Message;
import org.cst8277.Muhammad_Tariq.Entity.Users;
import org.cst8277.Muhammad_Tariq.Repository.MessageRepository;
import org.cst8277.Muhammad_Tariq.Repository.RoleRepo;
import org.cst8277.Muhammad_Tariq.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	private final MessageRepository messageRepo;
	private final UserRepository userRepo;
	
	public MessageService(MessageRepository messageRepo, UserRepository userRepo) {
		this.messageRepo = messageRepo;
		this.userRepo = userRepo;
	}
	
	
	 public Message saveMessage(String content, String userId) {
		 Users producer = userRepo.findByIdAndRoleProducer(userId)
	                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " is not a producer"));
	        // Create and save the message
	        Message message = new Message();
	        message.setContent(content);
	        message.setProducer(producer);
	        return messageRepo.save(message);
	    }
	
	public List<Message> getMessagesByUser(String userId){
		return messageRepo.findByProducerId(userId);
	}
	
	public List<Message> returnAll(){
		return messageRepo.findAll();
	}

	

}