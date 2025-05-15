package com.ecolink.spring.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ecolink.spring.entity.Challenge;
import com.ecolink.spring.entity.Company;
import com.ecolink.spring.entity.Ods;
import com.ecolink.spring.repository.ChallengeRepository;
import com.ecolink.spring.specification.ChallengeSpecification;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository repository;

    public Boolean existsByTitle(String title) {
        return repository.existsByTitle(title);
    }

    public Challenge findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public void save(Challenge challenge) {
        repository.save(challenge);
    }

    public List<Challenge> getAllChallenges() {
        return repository.findAll();
    }

    public List<Challenge> getChallengesByFilter(List<Ods> filterOds, BigDecimal pricemin,
            BigDecimal pricemax) {
        Specification<Challenge> spec = ChallengeSpecification.filters(filterOds, pricemin, pricemax);
        return repository.findAll(spec);
    }

    public Long getNumberChallenges() {
        return repository.count();
    }

    public Page<Challenge> findByFilterAndPagination(List<Ods> odsList, BigDecimal minprice, BigDecimal maxprice,
            int page, int size) {
        Specification<Challenge> spec = ChallengeSpecification.filters(odsList, minprice, maxprice);
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(spec, pageable);
    }

    public List<Challenge> getChallengesByRelevant() {
        return repository.findTop4ByEndDateGreaterThanEqualOrderByEndDateAsc(LocalDate.now());
    }

    public Challenge findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Challenge> getActiveChallenges() {

        return repository.findByendDateGreaterThanEqual(LocalDate.now());
    }

    public Challenge findByIdAndCompany(Long id, Company company) {
        return repository.findByIdAndCompany(id, company);
    }

    public void delete(Challenge actuaChallenge) {
        repository.delete(actuaChallenge);
    }
    public boolean togglePremium(Challenge challenge) {
    challenge.setPremium(!challenge.getPremium());
    repository.save(challenge);
    return challenge.getPremium();
    }

}
