package kr.gg.lol.domain.post.repository;

import kr.gg.lol.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDslRepository {
}
