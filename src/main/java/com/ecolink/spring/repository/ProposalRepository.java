package com.ecolink.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecolink.spring.entity.Challenge;
import com.ecolink.spring.entity.Proposal;
import com.ecolink.spring.entity.Startup;



@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    public boolean existsById(Long id);
    public Boolean existsByStartupAndChallenge(Startup startup, Challenge challenge);
    public List<Proposal> findByChallenge(Challenge challenge);
}
