package com.ecolink.spring.dto;

import com.ecolink.spring.entity.SubscriptionType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequest {
    private String cardNumber;
    private String cardExpiry;
    private String cardCVC;
    private SubscriptionType subscriptionType;
    private int durationDays;
}
