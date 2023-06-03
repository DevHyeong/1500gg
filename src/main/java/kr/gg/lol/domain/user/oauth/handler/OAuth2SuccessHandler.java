package kr.gg.lol.domain.user.oauth.handler;

import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import static kr.gg.lol.common.constant.OAuth2Constants.*;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private static final String DOMAIN = "http://localhost:3000";
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String target = generateTargetUrl(authentication);
        getRedirectStrategy().sendRedirect(request, response, target);
    }

    private String generateTargetUrl(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        boolean isRegisted = (boolean) oAuth2User.getAttributes().get(IS_REGISTED_USER);
        return isRegisted ? generateExistingUser(authentication) : generateNewUser(authentication);
    }

    private String generateExistingUser(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String jwtToken = tokenProvider.createToken(authentication);
        String path = SUCCESS_PATH;
        UriComponentsBuilder uriComponents =  UriComponentsBuilder.fromUriString(DOMAIN)
                .path(path)
                .queryParam("access_token", jwtToken)
                .queryParam("id", (String) oAuth2User.getAttribute("id"))
                .queryParam("social_type", oAuth2User.getName())
                .queryParam("nickname", URLEncoder.encode((String) oAuth2User.getAttributes().get("nickname"), Charset.forName("UTF-8")));
        return uriComponents.build().toUriString();
    }
    private String generateNewUser(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String jwtToken = tokenProvider.createTempToken(authentication);
        String path = JOIN_PATH;
        UriComponentsBuilder uriComponents =  UriComponentsBuilder.fromUriString(DOMAIN)
                .path(path)
                .queryParam("access_token", jwtToken)
                .queryParam("id", (String) oAuth2User.getAttribute("id"))
                .queryParam("social_type", oAuth2User.getName());
        return uriComponents.build().toUriString();
    }
}
