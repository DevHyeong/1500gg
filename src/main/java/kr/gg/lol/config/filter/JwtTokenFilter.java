package kr.gg.lol.config.filter;

import kr.gg.lol.domain.user.oauth.enums.OAuth2Provider;
import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2Factory;
import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2FactoryImpl;
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
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = tokenFromRequest(request);
            Map<String, Object> attributes = tokenProvider.getUserFromToken(token);
            SimpleOAuth2Factory simpleOAuth2Factory = SimpleOAuth2FactoryImpl.createOAuth2Factory(attributes);
            Authentication authentication = new UserAuthentication(simpleOAuth2Factory.createOAuth2User());
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
