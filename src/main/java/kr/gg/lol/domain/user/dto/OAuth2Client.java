package kr.gg.lol.domain.user.dto;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import static org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.*;


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
        public Properties(Provider provider, Registration registration){
            this.name = registration.getClientName();
            this.clientId = registration.getClientId();
            this.redirectUri = registration.getRedirectUri();
            this.authorizationUri = provider.getAuthorizationUri();
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

    public enum OAuth2Provider{
        NAVER("naver"),
        KAKAO("kakao");

        private final String provider;
        OAuth2Provider(String provider){
            this.provider = provider;
        }
    }

}
