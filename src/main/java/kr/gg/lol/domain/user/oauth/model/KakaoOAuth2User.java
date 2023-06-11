package kr.gg.lol.domain.user.oauth.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Setter
@Getter
public class KakaoOAuth2User implements OAuth2User {

    private Map<String, Object> attributes;

    public KakaoOAuth2User(Map<String, Object> attributes){

        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getName() {
        return SocialType.KAKAO.getValue();
    }
}
