package org.cst8277.Muhammad_Tariq.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
@Entity
public class Token {
	
	@Id
	@UuidGenerator
	private String Id;
	
	private String token;
	
	@Column(nullable=false)
	private LocalDateTime expirationTime;
	
	@ManyToOne
	@JoinColumn(nullable=false, unique=true)
	Users user;
	
	
	
	
	public String getId() {
		return Id;
	}
	
	
	public String getToken() {
		return token;
	}
	
	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}
	
	 public void setToken(String token) {
	        this.token = token;
	    }

	    

	    public void setExpirationTime(LocalDateTime expirationTime) {
	        this.expirationTime = expirationTime;
	    }
	    

	    public Users getUser() {
	        return user;
	    }

	    public void setUser(Users user) {
	        this.user = user;
	    }
	
}