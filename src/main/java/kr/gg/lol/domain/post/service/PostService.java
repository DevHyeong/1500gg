package kr.gg.lol.domain.post.service;

import kr.gg.lol.domain.post.dto.PostDto;
import kr.gg.lol.domain.post.dto.PostPageRequest;
import kr.gg.lol.domain.post.entity.Post;
import kr.gg.lol.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kr.gg.lol.domain.post.dto.PostDto.toDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostDto create(Long userId, PostDto postDto){
        Post post = new Post(userId, postDto);
        return toDto(postRepository.save(post));
    }

    public List<PostDto> posts(PostPageRequest pageRequest){
       if(pageRequest.hasNotId())
            return postRepository.findAllOrderByIdDesc(pageRequest.getSize());
        return postRepository.findAllByLessThanIdAndOrderByIdDesc(pageRequest.getId(), pageRequest.getSize());
    }

}
