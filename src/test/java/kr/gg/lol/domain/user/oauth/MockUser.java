package kr.gg.lol.domain.user.oauth;

import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import lombok.Getter;

@Getter
public class MockUser extends User {

    private Long id;
    private String nickname;
    private SocialType socialType;
    public MockUser(Long id, String nickname){
        this.id = id;
        this.nickname = nickname;
    }

    public MockUser(Long id, String nickname, SocialType socialType){
        this.id = id;
        this.nickname = nickname;
        this.socialType = socialType;
    }


}
