package kr.gg.lol.domain.user.entity;

import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import lombok.Getter;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "_user")
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
    private SocialType socialType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(){
    }

    public User(UserDto userDto){
        this.socialId = userDto.getSocialId();
        this.nickname = userDto.getNickname();
        this.socialType = userDto.getSocialType();
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
