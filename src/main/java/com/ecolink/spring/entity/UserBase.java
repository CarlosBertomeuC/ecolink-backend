package com.ecolink.spring.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Startup.class, name = "startup"),
        @JsonSubTypes.Type(value = Client.class, name = "client"),
        @JsonSubTypes.Type(value = Company.class, name = "company")
})
@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserBase implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    UserType userType;
    @Column(unique = true)
    String name;
    @Column(unique = true)
    String email;
    String password;
    String imageUrl;
    Long level = 0L;
    Long xp = 0L;
    LocalDate registerDate;
    boolean isVerified = false;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Comment> comments;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Subscription subscription;

    @PrePersist
    public void prePersist() {
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setType(SubscriptionType.FREE);
            subscription.setStartDate(LocalDate.now());
            subscription.setUser(this);
        }
    }

    public void addComment(Comment comment) {
        comment.setUser(this);
        this.comments.add(comment);
    }
    
    public String subscriptionType() {
        return subscription.getType().toString();
    }
    

    public void addXp(Long xpToAdd) {
        if (this.xp == null ||this.xp < 0) {
            this.xp = 0L;
        }
        this.xp += xpToAdd;

        this.level = calculateLevel(this.xp);
    }

    public void removeXp(Long xpToRemove) {
        this.xp -= xpToRemove;

        this.level = calculateLevel(this.xp);
    }

    public Long getXpForNextLevel() {
        Long nextLevelXp = 0L;
        switch (this.level.intValue()) {
            case 0:
                nextLevelXp = 30L;
                break;
            case 1:
                nextLevelXp = 100L;
                break;
            case 2:
                nextLevelXp = 250L;
                break;
            case 3:
                nextLevelXp = 500L;
                break;
            case 4:
                nextLevelXp = 1000L;
                break;
            case 5:

                nextLevelXp = 2000L;
                break;
            case 6:
                nextLevelXp = 5000L;
                break;
            case 7:
                nextLevelXp = 10000L;
                break;
            case 8:
                nextLevelXp = 20000L;
                break;
            case 9:
                nextLevelXp = 50000L;
                break;
            case 10:
                nextLevelXp = 100000L;
                break;
            case 11:
                nextLevelXp = 200000L;
                break;
            default:
                nextLevelXp = Long.MAX_VALUE;
        }
        return nextLevelXp;
    }

    private Long calculateLevel(Long xp) {
        if (xp >= 200000)
            return 12L;
        if (xp >= 100000)
            return 11L;
        if (xp >= 50000)
            return 10L;
        if (xp >= 20000)
            return 9L;
        if (xp >= 10000)
            return 8L;
        if (xp >= 5000)
            return 7L;
        if (xp >= 2000)
            return 6L;
        if (xp >= 1000)
            return 5L;
        if (xp >= 500)
            return 4L;
        if (xp >= 250)
            return 3L;
        if (xp >= 100)
            return 2L;
        if (xp >= 30)
            return 1L;
        return 0L;
    }
}
