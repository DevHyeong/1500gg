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
