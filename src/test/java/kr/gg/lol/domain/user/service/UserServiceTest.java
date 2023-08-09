package kr.gg.lol.domain.user.service;

import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.MockUser;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.oauth.model.NaverOAuth2User;
import kr.gg.lol.domain.user.oauth.model.UserAuthentication;
import kr.gg.lol.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static kr.gg.lol.common.constant.OAuth2Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private TokenProvider tokenProvider = new TokenProvider();

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, tokenProvider);
    }

    @Test
    @Disabled
    void testSignInThenReturnUser(){ // TODO : userService 내부로직에서 User 객체 생성시 모킹안되는 문제
        //given
        String token = createTempToken();
        UserDto userDto = new UserDto();
        userDto.setNickname("생각하는개발자");
        userDto.setAccessToken(token);
        User user = new MockUser(3L, "생각하는개발자", SocialType.NAVER);
        given(userRepository.save(user)).willReturn(user);

        UserDto userDto1 = new UserDto();
        userDto1.setNickname("생각하는개발자");
        userDto1.setAccessToken(token);

        //when
        UserDto result = userService.signIn(userDto);

        //then
        assertEquals("생각하는개발자", result.getNickname());

    }

    @Test
    void testLogout(){
        //given
        Map<String, Object> attributes = new HashMap<>();
        String token = createTempToken();
        attributes.put("accessToken", token);
        OAuth2User oAuth2User = new NaverOAuth2User(attributes);
        SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(oAuth2User));

        //when
        userService.logout();

        //then
        assertEquals(false, tokenProvider.validateToken(LocalDateTime.now(), token));
    }

    private String createTempToken(){
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();

        //response.put("id", "test1234");
        attributes.put("id", "test1234");
        attributes.put(REGISTRATION_ID, "naver");

        Authentication authentication = new UserAuthentication(new NaverOAuth2User(attributes));
        Date expiresAt = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
        return tokenProvider.createTempToken(authentication, expiresAt);
    }
}