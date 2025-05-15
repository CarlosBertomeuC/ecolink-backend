package com.ecolink.spring.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;

    @ManyToMany
    @JoinTable(name = "challenge_ods", joinColumns = @JoinColumn(name = "id_startup"), inverseJoinColumns = @JoinColumn(name = "id_ods"))
    private List<Ods> odsList;

    private String title;
    private String description;
    private String shortDescription;
    private BigDecimal budget;
    private LocalDate endDate;
    private LocalDate startDate;
    @Column(nullable = false)
    private Boolean premium = false;

    @OneToMany(mappedBy = "challenge", orphanRemoval = true)
    List<Proposal> proposals = new ArrayList<>();

    @ElementCollection
    @Column(columnDefinition = "TEXT")
    List<String> requirements = new ArrayList<>();
    @ElementCollection
    @Column(columnDefinition = "TEXT")
    List<String> benefits = new ArrayList<>();

    public Challenge(Company company, String title, String description, String shortDescription, BigDecimal budget,
            LocalDate endDate,
            List<Ods> odsList) {
        this.company = company;
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.budget = budget;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
        this.odsList = odsList;
    }

    public void addProposal(Proposal proposal) {
        this.proposals.add(proposal);
        if (proposal.getChallenge() == null) {

            proposal.setChallenge(this);
        }

    }

    public Integer getNumberOfParticipants() {
        return proposals.size();
    }

    public void addRequirement(String requirement) {
        this.requirements.add(requirement);
    }

    public void deleteRequirement(String requirement) {
        this.requirements.remove(requirement);
    }

    public void addBenefit(String benefit) {
        this.benefits.add(benefit);
    }

    public void deleteBenefit(String benefit) {
        this.benefits.remove(benefit);
    }
}
