package kr.gg.lol.domain.user.oauth.factory;

import kr.gg.lol.domain.user.oauth.enums.SocialType;
import kr.gg.lol.domain.user.oauth.model.NaverOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class NaverOAuth2Factory extends SimpleOAuth2Factory{

    public NaverOAuth2Factory(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public OAuth2User createOAuth2User() {
        attributes.put("socialType", SocialType.NAVER);
        return new NaverOAuth2User(attributes);
    }
}
