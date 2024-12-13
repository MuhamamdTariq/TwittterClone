package org.cst8277.Muhammad_Tariq.Repository;

import java.util.List;
import java.util.Optional;

import org.cst8277.Muhammad_Tariq.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, String> {
	List<Role> findAll();
	Optional<Role> findById(String roleId);
	Role findByName(String name);
	Optional<Role> findByNameAndId(String user_id, String name);
	

}