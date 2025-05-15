package com.ecolink.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.spring.dto.ChallengeDTO;
import com.ecolink.spring.dto.ChallengeFindDTO;
import com.ecolink.spring.dto.ChallengePostDTO;
import com.ecolink.spring.dto.DTOConverter;
import com.ecolink.spring.dto.PaginationResponse;
import com.ecolink.spring.entity.Challenge;
import com.ecolink.spring.entity.Company;
import com.ecolink.spring.entity.Ods;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.exception.ChallengeNotFoundException;
import com.ecolink.spring.exception.ErrorDetails;
import com.ecolink.spring.exception.PostNotFoundException;
import com.ecolink.spring.response.SuccessDetails;
import com.ecolink.spring.service.ChallengeService;
import com.ecolink.spring.service.CompanyService;
import com.ecolink.spring.service.OdsService;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/challenge")
@RequiredArgsConstructor
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;
    private final DTOConverter challengeDtoConverter;
    private final OdsService odsService;
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<?> getFilteredChallenges(
            @RequestParam(required = false) List<Long> odsIds,
            @RequestParam(required = false) BigDecimal minprice,
            @RequestParam(required = false) BigDecimal maxprice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            List<Ods> odsList = (odsIds != null && !odsIds.isEmpty()) ? odsService.findAllById(odsIds) : null;

            Page<Challenge> challenges = challengeService.findByFilterAndPagination(odsList, minprice, maxprice, page,
                    size);

            if (challenges.isEmpty()) {
                throw new ChallengeNotFoundException("No se han encontrado challenges con el filtrado");
            }

            List<ChallengeDTO> dtoList = challenges.getContent().stream()
                    .map(challengeDtoConverter::converChallengeToDto)
                    .collect(Collectors.toList());

            var response = new PaginationResponse<>(
                    dtoList,
                    challenges.getNumber(),
                    challenges.getSize(),
                    challenges.getTotalElements(),
                    challenges.getTotalPages(),
                    challenges.isLast());

            return ResponseEntity.ok(response);
        } catch (ChallengeNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @GetMapping("/home")
    public ResponseEntity<?> getRelevantChallenges() {
        try {

            List<Challenge> challenges = challengeService.getChallengesByRelevant();
            if (challenges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDetails(HttpStatus.NOT_FOUND.value(), "No se encontraron post relevantes"));
            }

            List<ChallengeDTO> dtoList = challenges.stream()
                    .map(challengeDtoConverter::converChallengeToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtoList);
        } catch (PostNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (Exception e) {

            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }

    }

    @GetMapping("/company")
    public ResponseEntity<?> getChallengesByCompany(@AuthenticationPrincipal UserBase user) {
        try {
            if (user == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be logged in");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            if (!(user instanceof Company)) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be a company");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            Company company = companyService.findById(user.getId());

            if (company == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                        "The company does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
            }

            List<ChallengeDTO> dtoList = company.getChallenges().stream()
                    .map(challengeDtoConverter::converChallengeToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtoList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChallenge(@PathVariable Long id) {
        try {
            Challenge challenge = challengeService.findById(id);
            if (challenge == null) {
                throw new ChallengeNotFoundException("No existe un challenge con id=" + id);
            }
            ChallengeFindDTO dto = challengeDtoConverter.converChallengeToChallengeFindDto(challenge);

            return ResponseEntity.ok(dto);
        } catch (ChallengeNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @PostMapping
    public ResponseEntity<?> createChallenge(@AuthenticationPrincipal UserBase user,
            @RequestBody ChallengePostDTO challenge) {
        try {
            if (user == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be logged in");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            if (!(user instanceof Company)) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be a company");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            Company company = (Company) user;

            if (challenge.getTitle() == null || challenge.getTitle().isEmpty()
                    || challenge.getShortDescription() == null || challenge.getShortDescription().isEmpty()
                    || challenge.getBudget() == null || challenge.getEndDate() == null || challenge.getEndDate() <= 0
                    || challenge.getOdsList() == null
                    || challenge.getOdsList().isEmpty()) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
                        "All fields are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
            }
            LocalDate endDate = LocalDate.now().plusDays(challenge.getEndDate());
            List<Ods> odsList = odsService.findAllById(challenge.getOdsList());

            Challenge newChallenge = new Challenge(company, challenge.getTitle(),
                    challenge.getDescription(),
                    challenge.getShortDescription(), challenge.getBudget(),
                    endDate, odsList);

            newChallenge.setRequirements(challenge.getRequirements());
            newChallenge.setBenefits(challenge.getBenefits());

            company.addXp(25L);
            challengeService.save(newChallenge);
            companyService.save(company);

            SuccessDetails successDetails = new SuccessDetails(HttpStatus.CREATED.value(),
                    "Challenge created successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(successDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChallenge(@AuthenticationPrincipal UserBase user, @PathVariable Long id,
            @RequestBody ChallengePostDTO challenge) {
        try {
            if (user == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be logged in");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            if (!(user instanceof Company)) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be a company");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            Company company = (Company) user;
            Challenge actuaChallenge = challengeService.findByIdAndCompany(id, company);

            if (actuaChallenge == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                        "The challenge does not exist or does not belong to the company");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
            }

            if (!challenge.getTitle().isEmpty()) {
                actuaChallenge.setTitle(challenge.getTitle());
            }

            if (!challenge.getDescription().isEmpty()) {
                actuaChallenge.setDescription(challenge.getDescription());
            }

            if (!challenge.getShortDescription().isEmpty()) {
                actuaChallenge.setShortDescription(challenge.getShortDescription());
            }

            if (challenge.getBudget() != null) {
                actuaChallenge.setBudget(challenge.getBudget());
            }

            if (challenge.getEndDate() != null && challenge.getEndDate() > 0) {
                actuaChallenge.setEndDate(LocalDate.now().plusDays(challenge.getEndDate()));
            }

            if (challenge.getOdsList() != null && !challenge.getOdsList().isEmpty()) {
                List<Ods> odsList = odsService.findAllById(challenge.getOdsList());
                actuaChallenge.setOdsList(odsList);
            }

            if (challenge.getRequirements() != null && !challenge.getRequirements().isEmpty()) {
                actuaChallenge.setRequirements(challenge.getRequirements());
            }

            if (challenge.getBenefits() != null && !challenge.getBenefits().isEmpty()) {
                actuaChallenge.setBenefits(challenge.getBenefits());
            }

            challengeService.save(actuaChallenge);

            SuccessDetails successDetails = new SuccessDetails(HttpStatus.OK.value(), "Challenge updated successfully");

            return ResponseEntity.status(HttpStatus.OK).body(successDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChallenge(@AuthenticationPrincipal UserBase user, @PathVariable Long id) {
        try {
            if (user == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be logged in");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            if (!(user instanceof Company)) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user must be a company");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            Company company = (Company) user;
            Challenge actuaChallenge = challengeService.findByIdAndCompany(id, company);

            if (actuaChallenge == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                        "The challenge does not exist or does not belong to the company");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
            }
            company.removeXp(25L);
            challengeService.delete(actuaChallenge);
            companyService.save(company);

            SuccessDetails successDetails = new SuccessDetails(HttpStatus.OK.value(), "Challenge deleted successfully");

            return ResponseEntity.status(HttpStatus.OK).body(successDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @PutMapping("/upgrade/{id}")
    public ResponseEntity<?> upgradeChallenge(@AuthenticationPrincipal UserBase user, @PathVariable Long id) {
        if (user == null || !(user instanceof Company)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDetails(
                    HttpStatus.UNAUTHORIZED.value(), "Only companies can upgrade challenges"));
        }

        Company company = (Company) user;
        Challenge challenge = challengeService.findByIdAndCompany(id, company);

        if (challenge == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(
                    HttpStatus.NOT_FOUND.value(), "Challenge not found or not owned by the company"));
        }

        boolean upgraded = challengeService.togglePremium(challenge);

        if (!upgraded) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(
                    HttpStatus.BAD_REQUEST.value(), "Challenge is already premium"));
        }

        return ResponseEntity.ok(new SuccessDetails(HttpStatus.OK.value(), "Challenge upgraded to premium"));
    }
}
