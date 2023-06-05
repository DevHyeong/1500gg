package kr.gg.lol.domain.post.entity;

import kr.gg.lol.domain.post.dto.PostDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String title;
    private String content;
    @Setter
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public Post(){

    }

    public Post(Long userId, PostDto target){
        this.userId = userId;
        this.title = target.getTitle();
        this.content = target.getContent();
    }

}
