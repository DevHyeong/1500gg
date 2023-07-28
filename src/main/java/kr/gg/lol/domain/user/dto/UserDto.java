package kr.gg.lol.domain.user.dto;

import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto{
    private Long id;
    private String socialId;
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;
    private String accessToken;
    private LocalDateTime expiredAt;
    private boolean authenticated;

    public UserDto(){

    }
    public UserDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.socialType = user.getSocialType();
    }
    public UserDto(User user, String accessToken, boolean authenticated){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.socialType = user.getSocialType();
        this.accessToken = accessToken;
        this.authenticated = authenticated;
    }


    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("nickname", nickname);
        jsonObject.put("socialType", socialType);
        return jsonObject.toString();
    }
}
