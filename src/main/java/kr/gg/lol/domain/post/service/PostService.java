package kr.gg.lol.domain.post.service;

import kr.gg.lol.domain.post.dto.PostDto;
import kr.gg.lol.domain.post.entity.Post;
import kr.gg.lol.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.gg.lol.domain.post.dto.PostDto.toDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostDto create(PostDto postDto){
        Post post = new Post(postDto);
        return toDto(postRepository.save(post));
    }

}
