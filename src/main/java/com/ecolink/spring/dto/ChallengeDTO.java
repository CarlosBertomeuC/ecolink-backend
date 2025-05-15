package com.ecolink.spring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeDTO {
    Long id;
    String companyName;
    String title;
    String shortDescription;
    BigDecimal budget;
    LocalDate endDate;
    List<OdsWithoutIdDTO> odsList;
    Integer numberOfParticipans;
    List<String> requirements;
    List<String> benefits;
    private Boolean premium;
}
