package com.ecolink.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecolink.spring.entity.Challenge;
import com.ecolink.spring.entity.Proposal;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.repository.ProposalRepository;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository repository;


    public Boolean existsById(Long id){

        return repository.existsById(id);
    }
    public Boolean existsByStartupAndChallenge(Startup startup, Challenge challenge){
        return repository.existsByStartupAndChallenge(startup, challenge);
    }
    public void save (Proposal proposal){
         repository.save(proposal);
    }
    public List<Proposal> findByChallenge(Challenge challenge) {
        return repository.findByChallenge(challenge);
    }
}
