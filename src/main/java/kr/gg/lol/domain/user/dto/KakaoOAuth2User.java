package kr.gg.lol.domain.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Setter
@Getter
public class KakaoOAuth2User implements OAuth2User {
    private Long id;

    @Override
    public Map<String, Object> getAttributes() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, Map.class);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getName() {
        return null;
    }
}
