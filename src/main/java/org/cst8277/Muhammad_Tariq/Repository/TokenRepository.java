package org.cst8277.Muhammad_Tariq.Repository;


import java.util.Optional;

import org.cst8277.Muhammad_Tariq.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<Token, String> {
	Token findByToken(String token);
	Optional<Token> findByUserId(String userId);

}