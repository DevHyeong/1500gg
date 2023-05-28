package kr.gg.lol.domain.post.entity;

import kr.gg.lol.domain.post.dto.PostDto;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;


@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private String title;
    private String content;

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

    public Post(PostDto target){
        copyProperties(this, target);
    }

}
