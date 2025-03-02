package com.ecolink.spring.dto;

import java.time.LocalDate;

import com.ecolink.spring.entity.SubscriptionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private Long userId;
    private SubscriptionType type;
    private LocalDate startDate;
    private LocalDate endDate;
}
