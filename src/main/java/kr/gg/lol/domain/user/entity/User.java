package kr.gg.lol.domain.user.entity;

import kr.gg.lol.domain.user.dto.UserDto;
import lombok.Getter;

import static kr.gg.lol.domain.user.dto.OAuth2Client.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String socialId;
    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    private OAuth2Provider provider;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(){

    }
    public User(Long id, String nickname, OAuth2Provider provider){
        this.id = id;
        this.nickname = nickname;
        this.provider = provider;
    }

    public User(UserDto userDto){
        this.socialId = userDto.getId();
        this.nickname = userDto.getNickname();
        this.provider = userDto.getProvider();
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
