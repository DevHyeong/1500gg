package kr.gg.lol.web.controller;

import kr.gg.lol.domain.post.dto.PostDto;
import kr.gg.lol.domain.post.service.PostService;
import kr.gg.lol.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    public List<PostDto> posts(){
        return new ArrayList<>();
    }




}
