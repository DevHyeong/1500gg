package kr.gg.lol.web.controller;

import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static kr.gg.lol.common.util.ApiUtils.*;

import java.net.URI;
import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class UserRestController {

//    CommonOAuth2Provider commonOAuth2Provider;
//    OAuth2ClientProperties oAuth2ClientProperties;
    private final UserService userService;


//
//    @GetMapping("/oauth2/{socialType}")
//    public void callbackOAuth2(HttpServletResponse response,
//                         @PathVariable final String socialType, String code) throws IOException {
//        OAuth2TokenResponse oAuth2TokenResponse = oAuth2Service.tokenRequest(OAuth2Provider.valueOf(socialType), code);
//        OAuth2User user = oAuth2Service.userInfoRequest(OAuth2Provider.valueOf(socialType), oAuth2TokenResponse.getAccess_token());
//
//        if(SecurityContextHolder.getContext().getAuthentication() instanceof UserDto){
//            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication();
//            response.sendRedirect("http://localhost:3000/success?nickname="
//                    + URLEncoder.encode(userDto.getNickname(), Charset.forName("UTF-8"))
//                    + "&id=" + userDto.getId()
//                    + "&social_type=" + userDto.getProvider()
//                    + "&access_token=" + userDto.getAccessToken());
//        }else{
//            response.sendRedirect("http://localhost:3000/join?access_token=" + oAuth2TokenResponse.getAccess_token()
//                    + "&social_type=" + socialType
//                    + "&id=" + user.getName());
//        }
//    }

    @PostMapping("/validate")
    public ApiResult<Boolean> validateNickname(@RequestBody UserDto userDto){
        return success(userService.validateNickname(userDto.getNickname()));
    }

    @PostMapping("/signIn")
    public ApiResult<UserDto> signIn(@RequestBody UserDto userDto){
        return success(userService.signIn(userDto));
    }

    @PostMapping("/logout")
    public ApiResult<Boolean> logout(){
        userService.logout();
        return success(true);
    }
}
