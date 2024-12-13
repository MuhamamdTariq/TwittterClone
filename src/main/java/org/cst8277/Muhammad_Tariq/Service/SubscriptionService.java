package org.cst8277.Muhammad_Tariq.Service;

import org.cst8277.Muhammad_Tariq.Entity.Role;
import org.cst8277.Muhammad_Tariq.Entity.RoleType;
import org.cst8277.Muhammad_Tariq.Entity.Subscription;
import org.cst8277.Muhammad_Tariq.Entity.Users;
import org.cst8277.Muhammad_Tariq.Repository.RoleRepo;
import org.cst8277.Muhammad_Tariq.Repository.SubscriptionRepository;
import org.cst8277.Muhammad_Tariq.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepo;


    public String subscribe(String subscriberId, String producerId) {
        // Retrieve the users by ID
        Users subscriber = userRepo.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        Users producer = userRepo.findById(producerId)
                .orElseThrow(() -> new RuntimeException("Producer not found"));

        // Check if the subscriber has the SUBSCRIBER role
        if (!subscriber.getRole().getName().equals(RoleType.SUBSCRIBER)) {
            throw new RuntimeException("User is not a subscriber");
        }

        // Check if the producer has the PRODUCER role
        if (!producer.getRole().getName().equals(RoleType.PRODUCER)) {
            throw new RuntimeException("User is not a producer");
        }

        // Check if the subscription already exists
        if (subscriptionRepository.existsBySubscriberAndProducer(subscriber, producer)) {
            return "Already subscribed";
        }

        // Create and save the subscription
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setProducer(producer);

        subscriptionRepository.save(subscription);

        return "Subscription successful";
    }
}