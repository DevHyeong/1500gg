package kr.gg.lol.domain.user.oauth.handler;

import kr.gg.lol.common.ConfigFileProperties;
import kr.gg.lol.common.util.TestUriCompoents;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.oauth.model.NaverOAuth2User;
import kr.gg.lol.domain.user.oauth.model.UserAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.*;
import static kr.gg.lol.common.util.TestUriCompoents.generate;
import static org.junit.jupiter.api.Assertions.*;

class OAuth2SuccessHandlerTest {

    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @BeforeEach
    void setup(){
        TokenProvider tokenProvider = new TokenProvider();
        ConfigFileProperties configFileProperties = new ConfigFileProperties();
        this.oAuth2SuccessHandler = new OAuth2SuccessHandler(tokenProvider, configFileProperties);
    }

    @Test
    void testGenerateTargetUrlWhenUserIsNotExist(){
        //given
        Authentication authentication = naverOAuth2UserWhenUserIsNotExist();

        //when
        String targetUrl = this.oAuth2SuccessHandler.generateTargetUrl(authentication);

        //then
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        TestUriCompoents testUriCompoents = generate(targetUrl);

        assertEquals(testUriCompoents.getHost(), "http://localhost:3000");
        assertEquals(testUriCompoents.getPath(), "join");
        assertEquals(testUriCompoents.getParams().get("id"), user.getAttribute("id"));
        assertEquals(testUriCompoents.getParams().get("social_type"), user.getName());
    }

    @Test
    void testGenerateTargetUrlWhenUserIsExist(){
        //given
        Authentication authentication = naverOAuth2UserWhenUserIsExist();

        //when
        String targetUrl = this.oAuth2SuccessHandler.generateTargetUrl(authentication);

        //then
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        TestUriCompoents testUriCompoents = generate(targetUrl);

        assertEquals(testUriCompoents.getHost(), "http://localhost:3000");
        assertEquals(testUriCompoents.getPath(), "success");
        assertEquals(testUriCompoents.getParams().get("id"), user.getAttribute("id"));
        assertEquals(Long.valueOf(testUriCompoents.getParams().get("userId")), user.getAttribute("userId"));
        assertEquals(testUriCompoents.getParams().get("social_type"), user.getName());
        assertEquals(testUriCompoents.getParams().get("nickname"), URLEncoder.encode((String) user.getAttributes().get("nickname"), Charset.forName("UTF-8")));
    }

    private Authentication naverOAuth2UserWhenUserIsExist(){
        Map<String, Object> attributes = new HashMap<>();

        attributes.put(IS_USER_REGISTED, true);
        attributes.put(ID, "test");
        attributes.put(USER_ID, 1L);
        attributes.put(NICKNAME, "생각하는개발자");
        attributes.put(EXPIRED_AT, Instant.parse("2100-07-22T12:34:56Z"));

        return new UserAuthentication(new NaverOAuth2User(attributes));
    }

    private Authentication naverOAuth2UserWhenUserIsNotExist(){
        Map<String, Object> attributes = new HashMap<>();

        attributes.put(IS_USER_REGISTED, false);
        attributes.put(ID, "test");

        return new UserAuthentication(new NaverOAuth2User(attributes));
    }
}