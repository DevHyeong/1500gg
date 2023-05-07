package kr.gg.lol.domain.user.dto;

import lombok.Getter;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.*;


import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/***
 *
 *  어플리케이션에서 제공되는 OAuth2Provider를 관리한다.
 *
 */

public class OAuth2Client {

    public static List<Properties> properties(OAuth2ClientProperties oAuth2ClientProperties){
        return Arrays.stream(OAuth2Provider.values())
                .map(e-> {
                    Provider provider = oAuth2ClientProperties.getProvider().get(e.provider);
                    Registration registration = oAuth2ClientProperties.getRegistration().get(e.provider);
                    return new Properties(provider, registration);
                }).collect(Collectors.toList());
    }
    public static class Properties{
        private final String name;
        private final String clientId;
        private final String redirectUri;
        private final String authorizationUri;
        private final String tokenUri;
        private final String state;
        public Properties(Provider provider, Registration registration){
            this.name = registration.getClientName();
            this.clientId = registration.getClientId();
            this.redirectUri = registration.getRedirectUri();
            this.authorizationUri = provider.getAuthorizationUri();
            this.tokenUri = provider.getTokenUri();
            this.state = generateState();
        }
        public String generateState(){
            SecureRandom random = new SecureRandom();
            return new BigInteger(130, random).toString(32);
        }

        public String getState() {
            return state;
        }

        public String getName(){
            return name;
        }
        public String getAuthorizationUri() {
            return authorizationUri;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public String getClientId() {
            return clientId;
        }

    }
    @Getter
    public enum OAuth2Provider{
        NAVER("naver"){
            @Override
            public URI tokenRequest(String code, OAuth2ClientProperties oAuth2ClientProperties) {
                Provider authProvider = oAuth2ClientProperties.getProvider().get(this.getProvider());
                Registration authRegistration = oAuth2ClientProperties.getRegistration().get(this.getProvider());
                return UriComponentsBuilder
                        .fromUriString(authProvider.getTokenUri())
                        .queryParam("client_id", authRegistration.getClientId())
                        .queryParam("client_secret", authRegistration.getClientSecret())
                        .queryParam("grant_type", authRegistration.getAuthorizationGrantType())
                        .queryParam("code", code)
                        .encode()
                        .build()
                        .toUri();
            }
        },
        KAKAO("kakao"){
            @Override
            public URI tokenRequest(String code, OAuth2ClientProperties oAuth2ClientProperties) {
                Provider authProvider = oAuth2ClientProperties.getProvider().get(this.getProvider());
                Registration authRegistration = oAuth2ClientProperties.getRegistration().get(this.getProvider());
                return UriComponentsBuilder
                        .fromUriString(authProvider.getTokenUri())
                        .queryParam("client_id", authRegistration.getClientId())
                        .queryParam("client_secret", authRegistration.getClientSecret())
                        .queryParam("grant_type", authRegistration.getAuthorizationGrantType())
                        .queryParam("redirect_uri", "http://localhost:3000" + authRegistration.getRedirectUri())
                        .queryParam("code", code)
                        .encode()
                        .build()
                        .toUri();
            }
        };

        private final String provider;
        OAuth2Provider(String provider){
            this.provider = provider;
        }

        public abstract URI tokenRequest(String code, OAuth2ClientProperties oAuth2ClientProperties);

    }

}
