package kr.gg.lol.domain.user.oauth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.MockUser;
import kr.gg.lol.domain.user.oauth.TestClientRegistrations;
import kr.gg.lol.domain.user.oauth.TestOAuth2AccessTokens;
import kr.gg.lol.domain.user.repository.UserRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static kr.gg.lol.common.constant.OAuth2Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class OAuth2UserServiceTest {
    private ClientRegistration.Builder clientRegistrationBuilder;
    private OAuth2AccessToken accessToken;
    private OAuth2UserService userService;
    private MockWebServer server;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        this.userService = new OAuth2UserService(userRepository);
        this.server = new MockWebServer();
        this.server.start();
        this.clientRegistrationBuilder = TestClientRegistrations.clientRegistration()
                .userInfoUri(null)
                .userNameAttributeName(null);
        this.accessToken = TestOAuth2AccessTokens.noScopes();
    }

    @AfterEach
    public void cleanup() throws Exception {
        this.server.shutdown();
    }
    @Test
    void testLoadUser() {
        //given
        String userInfoResponse = "{\n"
                + "   \"response\": {\n"
                + "   \"id\": \"mockid\",\n"
                + "   \"email\": \"user1@example.com\"\n"
                + "}\n"
                + "}\n";

        this.server.enqueue(jsonResponse(userInfoResponse));
        String userInfoUri = this.server.url("/user").toString();

        ClientRegistration clientRegistration = this.clientRegistrationBuilder.userInfoUri(userInfoUri)
                .userInfoAuthenticationMethod(AuthenticationMethod.HEADER).userNameAttributeName("response").build();
        User mockUser = new MockUser(1L, "생각하는개발자");
        given(userRepository.findBySocialId("mockid")).willReturn(Optional.of(mockUser));

        //when
        OAuth2User user = userService.loadUser(new OAuth2UserRequest(clientRegistration, this.accessToken));

        //then
        assertTrue((boolean) user.getAttribute(IS_REGISTED_USER));
        assertEquals(this.accessToken.getExpiresAt(), user.getAttribute(EXPIRED_AT));
        assertEquals(mockUser.getNickname(), user.getAttribute(NICKNAME));
        assertEquals(mockUser.getId(), (Long) user.getAttribute("userId"));
    }
    private MockResponse jsonResponse(String json){
        return new MockResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).setBody(json);
    }
}