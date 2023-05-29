package kr.gg.lol.domain.user.oauth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.REGISTRATION_ID;

@Service
public class TokenProvider {

    public String createToken(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Instant instant = (Instant) oAuth2User.getAttributes().get("expiresAt");
        Date expiresAt = Date.from(instant);

        return Jwts.builder()
                .setSubject(oAuth2User.getName())
                //.claim("nickname", oAuth2User.getAttr)
                .claim(REGISTRATION_ID, oAuth2User.getAttributes().get(REGISTRATION_ID))
                .setIssuedAt(new Date())
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, "WG3SWsDcaaagndmg985j930bcS00AxTC7G2dgRiqVyU=")
                .compact();
    }

    public Map<String, Object> getUserFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey("WG3SWsDcaaagndmg985j930bcS00AxTC7G2dgRiqVyU=")
                .parseClaimsJws(token)
                .getBody();

        Map<String, Object> map = new HashMap<>();
        map.put("expires_at", claims.getExpiration());
        map.put("id", claims.getSubject());
        map.put(REGISTRATION_ID, claims.get(REGISTRATION_ID));

        return map;
    }
    public boolean validateToken(LocalDateTime now, String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey("WG3SWsDcaaagndmg985j930bcS00AxTC7G2dgRiqVyU=")
                    .parseClaimsJws(token)
                    .getBody();
            LocalDateTime expiredAt = claims.getExpiration().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return now.isBefore(expiredAt);
        }catch (MalformedJwtException ex){
            return false;
        }
    }

    public void revokeToken(String token){

    }


}
