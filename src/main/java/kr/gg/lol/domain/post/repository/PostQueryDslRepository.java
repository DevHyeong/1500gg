package kr.gg.lol.domain.post.repository;

import kr.gg.lol.domain.post.dto.PostDto;
import kr.gg.lol.domain.post.entity.Post;

import java.util.List;

public interface PostQueryDslRepository{
    List<PostDto> findAllByLessThanIdAndOrderByIdDesc(Long id, Integer size);
    List<PostDto> findAllOrderByIdDesc(Integer size);
}
