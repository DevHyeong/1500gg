package kr.gg.lol.web.controller;

import kr.gg.lol.domain.user.dto.OAuth2Client;
import kr.gg.lol.domain.user.dto.OAuth2TokenResponse;
import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.service.OAuth2Service;
import kr.gg.lol.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.gg.lol.common.util.ApiUtils.*;
import static kr.gg.lol.domain.user.dto.OAuth2Client.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class UserRestController {

//    CommonOAuth2Provider commonOAuth2Provider;
//    OAuth2ClientProperties oAuth2ClientProperties;
    private final OAuth2Service oAuth2Service;
    private final UserService userService;
    @GetMapping("/oauth2/login-uri")
    public ApiResult<List<Properties>> properties(){
        return success(oAuth2Service.getOAuth2ClientProperties());
    }

    @GetMapping("/oauth2/{socialType}")
    public void callbackOAuth2(HttpServletResponse response,
                         @PathVariable final String socialType, String code) throws IOException {
        OAuth2TokenResponse oAuth2TokenResponse = oAuth2Service.tokenRequest(OAuth2Provider.valueOf(socialType), code);
        OAuth2User user = oAuth2Service.userInfoRequest(OAuth2Provider.valueOf(socialType), oAuth2TokenResponse.getAccess_token());

        if(SecurityContextHolder.getContext().getAuthentication() instanceof UserDto){
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication();
            System.out.println(userDto.getNickname());
            response.sendRedirect("http://localhost:3000/success?nickname="
                    + URLEncoder.encode(userDto.getNickname(), Charset.forName("UTF-8"))
                    + "&id=" + userDto.getId()
                    + "&social_type=" + userDto.getProvider()
                    + "&access_token=" + userDto.getAccessToken());
        }else{
            response.sendRedirect("http://localhost:3000/join?access_token=" + oAuth2TokenResponse.getAccess_token()
                    + "&social_type=" + socialType
                    + "&id=" + user.getName());
        }
    }

    @PostMapping("/validate")
    public ApiResult<Boolean> validateNickname(@RequestBody UserDto userDto){
        return success(userService.validateNickname(userDto.getNickname()));
    }

    @PostMapping("/signIn")
    public ApiResult<UserDto> signIn(@RequestBody UserDto userDto){
        return success(userService.signIn(userDto));
    }

    @GetMapping("/logout")
    public ApiResult<Boolean> logout(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.clearContext();
        return success(oAuth2Service.logout(userDto));
    }
//
//    @GetMapping("/oauth2/{socialType}/userInfo")
//    public ApiResult<Boolean> userInfo(@PathVariable final String socialType, String accessToken){
//        OAuth2User user = oAuth2Service.userInfoRequest(OAuth2Provider.valueOf(socialType), accessToken);
//        return success(true);
//    }


}