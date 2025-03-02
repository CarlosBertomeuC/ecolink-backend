package com.ecolink.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubscriptionType type; // Enum con los tipos de suscripción

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserBase user;
}

