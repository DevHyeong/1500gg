package kr.gg.lol.domain.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gg.lol.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minidev.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Nonnull;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;

@Getter
@Setter
public class UserDto implements Authentication {
    private String id;
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    private OAuth2Client.OAuth2Provider provider;
    private String accessToken;
    public UserDto(){

    }
    public UserDto(User user){
        this.id = user.getSocialId();
        this.nickname = user.getNickname();
        this.provider = user.getProvider();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("nickname", nickname);
        jsonObject.put("provider", provider);
        return jsonObject.toString();
    }
}
