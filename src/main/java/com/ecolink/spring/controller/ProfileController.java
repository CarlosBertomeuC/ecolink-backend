package com.ecolink.spring.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecolink.spring.dto.ClientProfileDTO;
import com.ecolink.spring.dto.CompanyProfileDTO;
import com.ecolink.spring.dto.DTOConverter;
import com.ecolink.spring.dto.StartupPrivateProfileDTO;
import com.ecolink.spring.entity.Client;
import com.ecolink.spring.entity.ClientMission;
import com.ecolink.spring.entity.Company;
import com.ecolink.spring.entity.Post;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.exception.ClientNotFoundException;
import com.ecolink.spring.exception.CompanyNotFoundException;
import com.ecolink.spring.exception.ErrorDetails;
import com.ecolink.spring.exception.StartupNotFoundException;
import com.ecolink.spring.service.ClientMissionService;
import com.ecolink.spring.service.ClientService;
import com.ecolink.spring.service.CompanyService;
import com.ecolink.spring.service.LikeService;
import com.ecolink.spring.service.StartupService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/profile")
@RestController
public class ProfileController {

    private final ClientMissionService clientMissionService;
    private final DTOConverter dtoConverter;
    private final LikeService likeService;
    private final CompanyService companyService;
    private final StartupService startupService;
    private final ClientService clientService;
    @GetMapping
    public ResponseEntity<?> getProfileUser(@AuthenticationPrincipal UserBase user) {
        try {
            if (user instanceof Client) {
                Client client = clientService.findById(user.getId());
                if (client == null) {
                    throw new ClientNotFoundException("Not found any client with the id: " + user.getId());
                    
                }
                List<ClientMission> completedMission = clientMissionService.getClientMission(client);
                List<Post> likedPostByTheUser = likeService.getPostLikedByUser(client);
                ClientProfileDTO clientProfile = dtoConverter.convertClientToClientProfileDTO(client, completedMission, likedPostByTheUser);
                return ResponseEntity.ok(clientProfile);
            } else if (user instanceof Startup) {
                Startup startup = startupService.findById(user.getId());
                if (startup == null) {
                    throw new ClientNotFoundException("Not found any client with the id: " + user.getId());
                }
                List<Post> likedPostByTheUser = likeService.getPostLikedByUser(startup);
                StartupPrivateProfileDTO startupProfile = dtoConverter.convertStartupToStartupPrivateProfile(startup, likedPostByTheUser);
                return ResponseEntity.ok(startupProfile);
            } else if (user instanceof Company) {
                Company company = companyService.getCompanyById(user.getId());
                if (company == null) {
                    throw new CompanyNotFoundException("Not found any company with the id: " + user.getId());
                }
                List<Post> likedPostByTheUser = likeService.getPostLikedByUser(company);
                CompanyProfileDTO companyProfile = dtoConverter.convetCompanyToCompanyProfileDTO(company, likedPostByTheUser);
                return ResponseEntity.ok(companyProfile);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User not supported, only Client, Startup and Company are supported");
            }
        } catch (ClientNotFoundException | StartupNotFoundException | CompanyNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }
}
