package com.ecolink.spring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeFindDTO {
    private String title;
    private String description;
    private String shortDescription;
    private BigDecimal budget;
    private LocalDate startDate;
    private LocalDate endDate;
    private CompanyForChallengeDTO company;
    List<String> requirements;
    List<String> benefits;
    private Integer numberOfParticipans;
    List<OdsWithoutIdDTO> odsList;
    Boolean premium;
}
