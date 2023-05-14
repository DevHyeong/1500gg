package kr.gg.lol.config.filter;

import kr.gg.lol.domain.user.oauth.enums.OAuth2Provider;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.oauth.model.UserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.REGISTRATION_ID;
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = tokenFromRequest(request);
            Map<String, Object> attributes = tokenProvider.getUserFromToken(token);
            OAuth2Provider provider = OAuth2Provider.from( (String) attributes.get(REGISTRATION_ID));
            Authentication authentication = new UserAuthentication(provider.getOAuth2User(attributes));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            log.error("{}", e);
        }
        filterChain.doFilter(request, response);

    }

    private String tokenFromRequest(HttpServletRequest request){
        String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        return value.replace("Bearer ", "");
    }
}
