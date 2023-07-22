package kr.gg.lol.domain.user.oauth.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import kr.gg.lol.domain.user.oauth.model.NaverOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.REGISTRATION_ID;
import static org.junit.jupiter.api.Assertions.*;

class TokenProviderTest {

    @Test
    void generateSecretKey(){
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);
    }

    @Test
    void testCreateJwtToken(){
        Authentication authentication = new MockAuthentication();
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        TokenProvider provider = new TokenProvider();
        String token = provider.createToken(authentication);
        Map<String, Object> attributes = provider.getUserFromToken(token);

        Long userId = Long.parseLong(String.valueOf(attributes.get("userId")));
        //assertEquals(principal.getAttributes().get(REGISTRATION_ID), attributes.get(REGISTRATION_ID));
        assertEquals(Date.from((Instant) principal.getAttributes().get("expiresAt")), attributes.get("expires_at"));
        assertEquals(principal.getAttribute("id"), attributes.get("id"));
        assertEquals(principal.getAttribute("userId"), userId);
    }

    @Test
    void testIntegerToLong(){

    }

    @Test
    void testValidateToken(){
        TokenProvider tokenProvider = new TokenProvider();
        Authentication authentication = new MockAuthentication();
        String token = tokenProvider.createToken(authentication);

        assertEquals(false, tokenProvider.validateToken(LocalDateTime.now(), "asdfasdf.asdfdsf.sdfsdf"));
        assertEquals(false, tokenProvider.validateToken(LocalDateTime.of(2100,10,9,12, 1), token));
        assertEquals(true, tokenProvider.validateToken(LocalDateTime.of(2001,10,9,12, 1), token));
    }

    public class MockAuthentication implements Authentication{

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            Instant instant = Instant.parse("2099-10-22T10:15:30.00Z");

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("expiresAt", instant);
            attributes.put(REGISTRATION_ID, "naver");
            attributes.put("id", "123sdffff");
            attributes.put("userId", Long.valueOf(55));
            return new NaverOAuth2User(attributes);
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return null;
        }
    }

}