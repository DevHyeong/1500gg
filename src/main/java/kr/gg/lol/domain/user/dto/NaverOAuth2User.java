package kr.gg.lol.domain.user.dto;

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
@Setter
@Getter
public class NaverOAuth2User implements OAuth2User {

    private String resultcode;
    private String message;
    private Result response;

    @Setter
    @Getter
    public static class Result{
        private String id;
        private String nickname;
        private String name;
        private String email;
        @Enumerated(value = EnumType.STRING)
        private Gender gender;

        private String age;
        private String birthday;
        private String profile_image;
        private String birthyear;
        private String mobile;
    }
    @Override
    public Map<String, Object> getAttributes() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this.response, Map.class);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return response.id;
    }

    public enum Gender{
        F,
        M,
        U;
    }
}
