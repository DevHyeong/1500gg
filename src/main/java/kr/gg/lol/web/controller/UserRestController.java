package kr.gg.lol.web.controller;

import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static kr.gg.lol.common.util.ApiUtils.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class UserRestController {

//    CommonOAuth2Provider commonOAuth2Provider;
//    OAuth2ClientProperties oAuth2ClientProperties;
    private final UserService userService;
    private final TokenProvider tokenProvider;


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
        if(!tokenProvider.validateToken(LocalDateTime.now(), userDto.getAccessToken()))
            throw new IllegalArgumentException("정상적인 접근이 아닙니다.");
        return success(userService.signIn(userDto));
    }

    @PostMapping("/logout")
    public ApiResult<Boolean> logout(){
        userService.logout();
        return success(true);
    }

    @GetMapping("/validateToken")
    public ApiResult<Boolean> validateToken(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer", "");
        if(StringUtils.isNullOrEmpty(token)){
            return success(false);
        }
        return success(tokenProvider.validateToken(LocalDateTime.now(), token));
        //return success(true);
    }


}
