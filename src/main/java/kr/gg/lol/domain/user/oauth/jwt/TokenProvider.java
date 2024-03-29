package kr.gg.lol.domain.user.oauth.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static kr.gg.lol.common.constant.OAuth2Constants.*;

@Service
public class TokenProvider {

    private final List<String> revokeTokens = new ArrayList<>();
    public String createToken(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Date expiresAt = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
        return Jwts.builder()
                .setSubject(oAuth2User.getName())
                .claim(ID, oAuth2User.getAttribute(ID))
                .claim(USER_ID, oAuth2User.getAttribute(USER_ID))
                .setIssuedAt(new Date())
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, "WG3SWsDcaaagndmg985j930bcS00AxTC7G2dgRiqVyU=")
                .compact();
    }

    /**
     *  temporary token for join
     *
     * */
    public String createTempToken(Authentication authentication, Date expiresAt){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(oAuth2User.getName())
                .claim(ID, oAuth2User.getAttribute(ID))
                .claim(USER_ID,  oAuth2User.getAttribute(USER_ID))
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
        map.put(EXPIRED_AT, claims.getExpiration());
        map.put(REGISTRATION_ID, claims.getSubject());
        map.put(ID, claims.get(ID));
        map.put(USER_ID, claims.get(USER_ID));
        return map;
    }
    public boolean validateToken(LocalDateTime now, String token){
        Optional<String> result = revokeTokens.stream()
                .filter(e-> e.equals(token))
                .findFirst();

        if(result.isPresent()){
            return false;
        }

        try{
            Claims claims = Jwts.parser()
                    .setSigningKey("WG3SWsDcaaagndmg985j930bcS00AxTC7G2dgRiqVyU=")
                    .parseClaimsJws(token)
                    .getBody();
            LocalDateTime expiredAt = claims.getExpiration().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return now.isBefore(expiredAt);
        }catch (MalformedJwtException ex) {
            return false;
        }catch (ExpiredJwtException ex){
            return false;
        }
    }

    public void revokeToken(String token){
        revokeTokens.add(token);
    }


}
