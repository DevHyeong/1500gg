package kr.gg.lol.web.controller;

import kr.gg.lol.domain.user.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.gg.lol.common.util.ApiUtils.*;
import static kr.gg.lol.domain.user.dto.OAuth2Client.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class UserRestController {

//    CommonOAuth2Provider commonOAuth2Provider;
//    OAuth2ClientProperties oAuth2ClientProperties;
    private final OAuth2Service oAuth2Service;

    @GetMapping("/oauth2/login-uri")
    public ApiResult<List<Properties>> properties(){
        return success(oAuth2Service.getOAuth2ClientProperties());
    }

    @GetMapping("/oauth2/kakao")
    public String oauth2KaKao(){

        return "kakao";
    }

    @GetMapping("/oauth2/naver")
    public String oauth2Naver(){
        return "naver";
    }

}
