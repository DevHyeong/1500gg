package kr.gg.lol.config.filter;

import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2FactoryImpl;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.oauth.model.UserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.ACCESS_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.indexOf("/api/post/") > -1 || uri.indexOf("/api/user/logout") > -1) {
            try {
                String token = tokenFromRequest(request);

                if (!tokenProvider.validateToken(LocalDateTime.now(), token)) {
                    throw new IllegalStateException();
                }

                Map<String, Object> attributes = tokenProvider.getUserFromToken(token);
                attributes.put(ACCESS_TOKEN, token);
                OAuth2User user = SimpleOAuth2FactoryImpl.createOAuth2User(attributes);
                Authentication authentication = new UserAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.error("{}", e);
                throw new IllegalStateException();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String tokenFromRequest(HttpServletRequest request){
        String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        return value.replace("Bearer ", "");
    }
}
