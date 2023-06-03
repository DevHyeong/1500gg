package kr.gg.lol.domain.user.oauth.enums;

public enum SocialType {
    KAKAO("kakao"),
    NAVER("naver");

    private final String value;

    SocialType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
