package com.ecolink.spring.service;

import com.ecolink.spring.entity.Subscription;
import com.ecolink.spring.entity.SubscriptionType;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void activateSubscription(UserBase user, SubscriptionType type, int durationDays) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setType(type);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(durationDays));
        subscriptionRepository.save(subscription);
    }

    public void cancelSubscription(UserBase user) {
        Subscription subscription = user.getSubscription();
        if (subscription != null) {
            subscriptionRepository.save(subscription);
        }
    }
    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }
}
