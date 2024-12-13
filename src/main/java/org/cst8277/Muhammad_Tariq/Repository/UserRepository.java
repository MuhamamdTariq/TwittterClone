package org.cst8277.Muhammad_Tariq.Repository;


import java.util.Optional;

import org.cst8277.Muhammad_Tariq.Entity.Role;
import org.cst8277.Muhammad_Tariq.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, String> {
	 @Query("SELECT u FROM Users u JOIN u.role r WHERE u.id = :userId AND r.name = 'PRODUCER'")
	    Optional<Users> findByIdAndRoleProducer(@Param("userId") String userId);

	Users findByUsername(String username);
	Optional<Users> findByEmail(String email);
	

}