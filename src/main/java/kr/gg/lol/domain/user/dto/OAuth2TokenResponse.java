package kr.gg.lol.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OAuth2TokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
    private String error;
    private String error_description;

}
