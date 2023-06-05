package kr.gg.lol.web.controller;

import static kr.gg.lol.common.util.ApiUtils.*;
import kr.gg.lol.domain.post.dto.PostDto;
import kr.gg.lol.domain.post.dto.PostPageRequest;
import kr.gg.lol.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static kr.gg.lol.common.util.ApiUtils.success;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping("/post/create")
    public PostDto create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody PostDto postDto){
        log.debug("{} ", oAuth2User);
        Integer userId = oAuth2User.getAttribute("userId");
        return postService.create(Long.parseLong(String.valueOf(userId)), postDto);
    }

    @GetMapping("/posts")
    public ApiResult<List<PostDto>> posts(PostPageRequest pageRequest){
        return success(postService.posts(pageRequest));
    }
}
