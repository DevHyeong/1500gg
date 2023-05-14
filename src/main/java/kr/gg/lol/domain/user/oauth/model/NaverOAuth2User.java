package kr.gg.lol.domain.user.oauth.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
@Getter
public class NaverOAuth2User implements OAuth2User {

    private Map<String, Object> attributes;

    public NaverOAuth2User(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return (String) attributes.get("id");
    }

}
