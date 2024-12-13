package org.cst8277.Muhammad_Tariq.Repository;

import org.cst8277.Muhammad_Tariq.Entity.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MessageRepository extends JpaRepository<Message, String> {
	List<Message> findByProducerId(String producerId);
	List<Message> findByContentContaining(String keyword);
	List<Message> findAll();

}