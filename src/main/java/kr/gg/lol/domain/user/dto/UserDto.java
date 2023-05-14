package kr.gg.lol.domain.user.dto;

import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.OAuth2Provider;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto{
    private String id;
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    private OAuth2Provider provider;
    private String accessToken;
    private LocalDateTime expiredAt;
    private boolean authenticated;

    public UserDto(){

    }
    public UserDto(User user){
        this.id = user.getSocialId();
        this.nickname = user.getNickname();
        this.provider = user.getProvider();
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
