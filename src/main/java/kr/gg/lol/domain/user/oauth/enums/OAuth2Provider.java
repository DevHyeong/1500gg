package kr.gg.lol.domain.user.oauth.enums;

import kr.gg.lol.domain.user.oauth.model.KakaoOAuth2User;
import kr.gg.lol.domain.user.oauth.model.NaverOAuth2User;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.REGISTRATION_ID;

@Getter
public enum OAuth2Provider {
    KAKAO("kakao"){
        @Override
        public OAuth2User getOAuth2User(Map<String, Object> attributes) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", attributes.get("id"));
            map.put(REGISTRATION_ID, this.getRegistrationId());
            return new KakaoOAuth2User(map);
        }

        @Override
        public void logout(Authentication authentication) {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            OAuth2Provider.logout(KAKAO_LOGOUT_URL, user.getAttribute("accessToken"));
        }
    },
    NAVER("naver"){
        @Override
        public OAuth2User getOAuth2User(Map<String, Object> attributes) {
            //Map<String, Object> map = (Map<String, Object>) attributes.get("response");
            attributes.put(REGISTRATION_ID, this.getRegistrationId());
            return new NaverOAuth2User(attributes);
        }

        @Override
        public void logout(Authentication authentication) {

        }
    };
    private final String registrationId;
    OAuth2Provider(String registrationId){
        this.registrationId = registrationId;
    }

    private static final String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";

    public abstract OAuth2User getOAuth2User(Map<String, Object> attributes);
    public abstract void logout(Authentication authentication);

    private static void logout(String url, String accessToken){
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity request = RequestEntity.post(url)
                    .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .build();
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        }catch(HttpClientErrorException e){
            throw new HttpClientErrorException(e.getStatusCode());
        }

    }
}
