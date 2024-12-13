package org.cst8277.Muhammad_Tariq.Repository;
import java.util.List;

import org.cst8277.Muhammad_Tariq.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
	List<Subscription> findBySubscriberId(String subscriberId);

	boolean existsBySubscriberAndProducer(Users subscriber, Users producer);

}