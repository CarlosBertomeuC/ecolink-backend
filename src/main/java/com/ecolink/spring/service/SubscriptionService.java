package com.ecolink.spring.service;

import com.ecolink.spring.entity.Subscription;
import com.ecolink.spring.entity.SubscriptionType;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.repository.SubscriptionRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void activateSubscription(UserBase user, SubscriptionType type, int durationDays) {
        Subscription subscription = user.getSubscription();
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setUser(user);
        }
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

    // Renueva las suscripciones que han expirado
    @Scheduled(cron = "0 0 0 * * ?") // Ejecuta a medianoche todos los días
    public void renewSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        for (Subscription subscription : subscriptions) {
            if (subscription.getEndDate() != null && subscription.getEndDate().isBefore(LocalDate.now())) {
                subscription.setStartDate(LocalDate.now());
                subscription.setEndDate(LocalDate.now().plusDays(30)); // Renueva por 30 días
                subscriptionRepository.save(subscription);
            }
        }
    }

}
