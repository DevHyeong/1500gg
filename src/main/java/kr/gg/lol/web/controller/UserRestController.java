package kr.gg.lol.web.controller;

import kr.gg.lol.domain.user.dto.OAuth2Client;
import kr.gg.lol.domain.user.dto.OAuth2TokenResponse;
import kr.gg.lol.domain.user.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/oauth2/{socialType}")
    public ApiResult<OAuth2TokenResponse> callbackOAuth2(HttpServletResponse response, @PathVariable final String socialType, String code){
        return success(oAuth2Service.tokenRequest(socialType, code));
    }

    @GetMapping("/oauth2/{socialType}/userInfo")
    public void userInfo(){

    }
}
