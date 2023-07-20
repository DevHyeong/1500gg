package kr.gg.lol.domain.user.oauth;

import kr.gg.lol.domain.user.entity.User;
import lombok.Getter;

@Getter
public class MockUser extends User {

    private Long id;
    private String nickname;
    public MockUser(Long id, String nickname){
        this.id = id;
        this.nickname = nickname;
    }
}
