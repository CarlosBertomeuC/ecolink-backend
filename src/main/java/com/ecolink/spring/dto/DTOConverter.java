package com.ecolink.spring.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ecolink.spring.entity.Category;
import com.ecolink.spring.entity.Challenge;
import com.ecolink.spring.entity.Client;
import com.ecolink.spring.entity.ClientMission;
import com.ecolink.spring.entity.Comment;
import com.ecolink.spring.entity.Company;
import com.ecolink.spring.entity.Like;
import com.ecolink.spring.entity.Mission;
import com.ecolink.spring.entity.Ods;
import com.ecolink.spring.entity.Order;
import com.ecolink.spring.entity.OrderLine;
import com.ecolink.spring.entity.Post;
import com.ecolink.spring.entity.Product;
import com.ecolink.spring.entity.Proposal;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.entity.UserBase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DTOConverter {

    private final ModelMapper modelMapper;

    public ProductDTO convertProductToDto(Product product) {
        ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
        String startupName = product.getStartup().getName();
        if (startupName != null) {
            productDto.setStartupName(startupName);
        }

        if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
            productDto.setImageUrl("productdefault.png");

        }
        return productDto;
    }

    public ProductRelevantDTO convertProductRelevantToDto(Product product) {
        ProductRelevantDTO productDto = modelMapper.map(product, ProductRelevantDTO.class);

        if (productDto.getImageUrl() == null || productDto.getImageUrl().isEmpty()) {
            productDto.setImageUrl("productdefault.png");
        }
        return productDto;
    }

    public ProposalDTO convertProposalToDto(Proposal proposal) {
        ProposalDTO proposalDto = modelMapper.map(proposal, ProposalDTO.class);
        ChallengeDTO challenge = this.converChallengeToDto(proposal.getChallenge());
        proposalDto.setChallenge(challenge);
        StartupDTO startup = this.convertStartupToDto(proposal.getStartup());
        proposalDto.setStartup(startup);
        return proposalDto;
    }

    public ProposalChallengeDTO convertProposalChallengeToDto(Proposal proposal) {
        ProposalChallengeDTO proposalChallengeDTO = modelMapper.map(proposal, ProposalChallengeDTO.class);
        proposalChallengeDTO.setStartup(modelMapper.map(proposal.getStartup(), StartupDTO.class));
        return proposalChallengeDTO;
    }

    public ProposalStartupDTO convertProposalStartupToDto(Proposal proposal) {
        ChallengeDTO challenges = this.converChallengeToDto(proposal.getChallenge());
        ProposalStartupDTO proposalStartupDTO = modelMapper.map(proposal, ProposalStartupDTO.class);
        proposalStartupDTO.setChallenge(challenges);
        return proposalStartupDTO;
    }

    public ProposalStartupProfileDTO convertProposalToProposalStartupProfileDTO(Proposal proposal) {
        ProposalStartupProfileDTO proposalStartupProfileDTO = modelMapper.map(proposal,
                ProposalStartupProfileDTO.class);
        proposalStartupProfileDTO.setChallengeTitle(proposal.getChallenge().getTitle());
        return proposalStartupProfileDTO;
    }

    public ProposalStartupProfileDTO convertProposaToProposalStartupProfileDTO(Proposal proposal) {

        return modelMapper.map(proposal, ProposalStartupProfileDTO.class);
    }

    public StartupProductPublicProfileDTO convertProductTStartupProductProfile(Product product) {
        StartupProductPublicProfileDTO productDto = modelMapper.map(product, StartupProductPublicProfileDTO.class);
        if (productDto.getImageUrl() == null || product.getImageUrl().isEmpty()) {
            productDto.setImageUrl("productdefault.png");

        }
        return modelMapper.map(product, StartupProductPublicProfileDTO.class);
    }

    public StartupDTO convertStartupToDto(Startup startup) {

        return modelMapper.map(startup, StartupDTO.class);
    }

    public StartupHomeDTO convertStartupHomeToDto(Startup startup) {
        return modelMapper.map(startup, StartupHomeDTO.class);
    }

    public CompanyForChallengeDTO convertCompanyHomeToDto(Company company) {
        return modelMapper.map(company, CompanyForChallengeDTO.class);
    }

    public StartupPublicProfileDTO convertStartupProfileToDto(Startup startup) {
        return modelMapper.map(startup, StartupPublicProfileDTO.class);
    }

    public StartupPrivateProfileDTO convertStartupToStartupPrivateProfile(Startup startup,
            List<Post> likedPostByTheUser) {
        StartupPrivateProfileDTO startupProfile = modelMapper.map(startup, StartupPrivateProfileDTO.class);
        List<PostProfileUserDTO> listLikedPost = likedPostByTheUser.stream()
                .map(this::convertPostToPostProfileDto)
                .collect(Collectors.toList());
        startupProfile.setListLikePost(listLikedPost);

        return startupProfile;
    }

    public StartupProductPrivateProfileDTO convertStartupProductToStartupProductPrivateProfileDTO(Startup startup) {

        return modelMapper.map(startup, StartupProductPrivateProfileDTO.class);
    }

    public PostProfileUserDTO convertPostToPostProfileDto(Post post) {

        return modelMapper.map(post, PostProfileUserDTO.class);
    }

    public PostRelevantDTO convertPostRelevantToDTO(Post post) {
        PostRelevantDTO postDto = modelMapper.map(post, PostRelevantDTO.class);
        if (postDto.getImageUrl() == null || postDto.getImageUrl().isEmpty()) {
            postDto.setImageUrl("postdefault.png");
        }
        return postDto;
    }

    public CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        UserBase user = comment.getUser();
        commentDTO.setId_user(user.getId());
        commentDTO.setImageUrl(user.getImageUrl());
        commentDTO.setName(user.getName());

        return commentDTO;
    }

    public PostStartupDTO convertPostStartupToDto(Post post) {
        PostStartupDTO postDto = modelMapper.map(post, PostStartupDTO.class);
        postDto.setLikesCount(post.getNumberLikes());
        postDto.setCommentsCount(post.getNumberComments());
        postDto.setPostDate(post.getPostDate());

        List<OdsWithoutIdDTO> odsDto = post.getOdsList().stream()
                .map(this::convertOdsWithoutIdToDto)
                .collect(Collectors.toList());
        postDto.setOdsList(odsDto);
        return postDto;
    }

    public PostItemPageDTO convertPostItemPageToDTO(Post post) {

        PostItemPageDTO postDto = modelMapper.map(post, PostItemPageDTO.class);

        postDto.setNumberComents(post.getNumberComments());
        postDto.setLikesCount(post.getNumberLikes());

        if (postDto.getImageUrl() == null || postDto.getImageUrl().isEmpty()) {
            postDto.setImageUrl("postdefault.png");

        }

        List<CommentDTO> commentsDTO = post.getComments().stream()
                .map(this::convertCommentToDTO)
                .collect(Collectors.toList());

        List<LikeDTO> likesDTO = post.getLikes().stream()
                .map(this::convertLikeToDto)
                .collect(Collectors.toList());

        postDto.setLikes(likesDTO);
        postDto.setComments(commentsDTO);

        return postDto;
    }

    public PostDTO convertPostToDto(Post post) {

        PostDTO postDto = modelMapper.map(post, PostDTO.class);

        List<CommentDTO> commentsDTO = post.getComments().stream()
                .map(this::convertCommentToDTO)
                .collect(Collectors.toList());

        List<LikeDTO> likesDTO = post.getLikes().stream()
                .map(this::convertLikeToDto)
                .collect(Collectors.toList());

        if (postDto.getImageUrl() == null || postDto.getImageUrl().isEmpty()) {
            postDto.setImageUrl("postdefault.png");

        }

        postDto.setLikes(likesDTO);
        postDto.setComments(commentsDTO);

        postDto.setImageStartup(post.getStartup().getImageUrl());

        postDto.setLikesCount(post.getNumberLikes());

        return postDto;
    }

    public MissionDTO convertMissionToDto(Mission mission) {
        return modelMapper.map(mission, MissionDTO.class);
    }

    public MissionProfileDTO convertMissionToMissionProfileDTO(Mission mission) {

        return modelMapper.map(mission, MissionProfileDTO.class);
    }

    public ClientProfileDTO convertClientToClientProfileDTO(Client client, List<ClientMission> completedMission,
            List<Post> likedPostByTheUser) {

        ClientProfileDTO clientProfileDto = modelMapper.map(client, ClientProfileDTO.class);
        List<MissionProfileDTO> completedMissionDto = completedMission.stream()
                .map(clientMission -> this.convertMissionToMissionProfileDTO(clientMission.getMission()))
                .collect(Collectors.toList());
        List<PostProfileUserDTO> listLikedPost = likedPostByTheUser.stream()
                .map(this::convertPostToPostProfileDto)
                .collect(Collectors.toList());
        clientProfileDto.setListLikePost(listLikedPost);
        clientProfileDto.setCompletedMissions(completedMissionDto);
        return clientProfileDto;
    }

    public ClientMissionDTO convertClientMissionDTO(Mission mission, boolean completed) {
        ClientMissionDTO clientMissionDto = modelMapper.map(mission, ClientMissionDTO.class);
        clientMissionDto.setCompleted(completed);
        return clientMissionDto;
    }

    public ChallengeDTO converChallengeToDto(Challenge challenge) {
        ChallengeDTO challengeDto = modelMapper.map(challenge, ChallengeDTO.class);
        Integer numberOfParticipants = challenge.getNumberOfParticipants();
        challengeDto.setNumberOfParticipans(numberOfParticipants > 0 ? numberOfParticipants : 0);
        List<OdsWithoutIdDTO> odsDto = challenge.getOdsList().stream()
                .map(this::convertOdsWithoutIdToDto)
                .collect(Collectors.toList());
        challengeDto.setOdsList(odsDto);
        challengeDto.setPremium(challenge.getPremium());
        return challengeDto;
    }

    public ChallengeFindDTO converChallengeToChallengeFindDto(Challenge challenge) {
        ChallengeFindDTO challengeDto = modelMapper.map(challenge, ChallengeFindDTO.class);
        Company company = challenge.getCompany();
        CompanyForChallengeDTO companyDto = modelMapper.map(company, CompanyForChallengeDTO.class);
        challengeDto.setCompany(companyDto);
        Integer numberOfParticipants = challenge.getNumberOfParticipants();
        challengeDto.setNumberOfParticipans(numberOfParticipants > 0 ? numberOfParticipants : 0);
        List<OdsWithoutIdDTO> odsDto = challenge.getOdsList().stream()
                .map(this::convertOdsWithoutIdToDto)
                .collect(Collectors.toList());
        challengeDto.setOdsList(odsDto);
        challengeDto.setPremium(challenge.getPremium());

        return challengeDto;
    }

    // Creo un DTO para solo almacenar el ID,Descripcion, y presupuesto de un reto
    public ChallengeBasicDTO converChallengeBasicToDTO(Challenge challenge) {
        ChallengeBasicDTO challengeDto = modelMapper.map(challenge, ChallengeBasicDTO.class);
        Integer numberOfParticipants = challenge.getNumberOfParticipants();
        challengeDto.setNumberOfParticipans(numberOfParticipants > 0 ? numberOfParticipants : 0);
        return challengeDto;
    }

    public ChallengeCompanyProfileDTO convertChallengeToChallengeCompanyProfileDTO(Challenge challenge) {
        ChallengeCompanyProfileDTO challengeProfileDto = modelMapper.map(challenge, ChallengeCompanyProfileDTO.class);
        Integer numberOfParticipants = challenge.getNumberOfParticipants();
        challengeProfileDto.setNumberOfParticipans(numberOfParticipants > 0 ? numberOfParticipants : 0);
        return challengeProfileDto;
    }

    public CompanyProfileDTO convetCompanyToCompanyProfileDTO(Company company, List<Post> likedPostByTheUser) {

        CompanyProfileDTO companyProfileDTO = modelMapper.map(company, CompanyProfileDTO.class);
        List<ChallengeCompanyProfileDTO> listChallenges = company.getChallenges().stream()
                .map(this::convertChallengeToChallengeCompanyProfileDTO)
                .collect(Collectors.toList());
        List<PostProfileUserDTO> listLikedPost = likedPostByTheUser.stream()
                .map(this::convertPostToPostProfileDto)
                .collect(Collectors.toList());
        companyProfileDTO.setListLikePost(listLikedPost);
        companyProfileDTO.setListChallengesCompany(listChallenges);
        return companyProfileDTO;
    }

    public CompanyDTO convertCompanyDTO(Company company) {

        CompanyDTO companyDto = modelMapper.map(company, CompanyDTO.class);
        List<ChallengeBasicDTO> challengeCompany = company.getChallenges().stream()
                .map(this::converChallengeBasicToDTO).collect(Collectors.toList());
        companyDto.setChallenges(challengeCompany);
        return companyDto;
    }

    public OdsDTO convertOdsToDto(Ods ods) {
        return modelMapper.map(ods, OdsDTO.class);
    }

    public CategoryDTO convertCategoryToDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public OdsWithoutIdDTO convertOdsWithoutIdToDto(Ods ods) {
        return modelMapper.map(ods, OdsWithoutIdDTO.class);
    }

    public LikeDTO convertLikeToDto(Like like) {
        LikeDTO likeDTO = modelMapper.map(like, LikeDTO.class);
        likeDTO.setId(like.getId());
        likeDTO.setId_user(like.getUser().getId());
        return likeDTO;
    }

    public GetUserDTO convertUserDTO(UserBase user) {
        return modelMapper.map(user, GetUserDTO.class);
    }

    public GetUserFrontDTO convertClientBaseToDto(Client client) {
        return modelMapper.map(client, GetUserFrontDTO.class);
    }

    public GetUserFrontDTO convertStartupBaseToDto(Startup startup) {
        return modelMapper.map(startup, GetUserFrontDTO.class);
    }

    public GetUserFrontDTO convertCompanypBaseToDto(Company company) {
        return modelMapper.map(company, GetUserFrontDTO.class);
    }

    public OrderDTO convertOrderToDTO(Order order) {
        List<OrderLineDTO> orderLinesDTO = order.getOrderLines().stream()
                .map(this::convertOrderLineToDTO)
                .collect(Collectors.toList());

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setOrderLines(orderLinesDTO);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLineDTO orderLineDTO : orderLinesDTO) {
            BigDecimal lineAmount = orderLineDTO.getProduct().getPrice()
                    .multiply(new BigDecimal(orderLineDTO.getAmount()));
            total = total.add(lineAmount);
        }
        orderDTO.setTotal(total);

        return orderDTO;
    }

    public OrderLineDTO convertOrderLineToDTO(OrderLine orderLine) {
        return modelMapper.map(orderLine, OrderLineDTO.class);
    }

    public UserPendingDTO convertCompanyToUserPendingDto(Company company) {
        return modelMapper.map(company, UserPendingDTO.class);
    }

    public UserPendingDTO convertStartupToUserPendingDto(Startup startup) {
        return modelMapper.map(startup, UserPendingDTO.class);
    }
}