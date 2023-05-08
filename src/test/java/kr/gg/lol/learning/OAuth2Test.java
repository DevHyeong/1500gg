package kr.gg.lol.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OAuth2Test {

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Test
    void testOAuth2ProviderNaver(){
        OAuth2ClientProperties.Provider provider = oAuth2ClientProperties.getProvider().get("naver");
        assertEquals("https://nid.naver.com/oauth2.0/authorize", provider.getAuthorizationUri());

        OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get("naver");
        assertEquals("V87zFuapy7EMvwR99qMT", registration.getClientId());
        assertEquals("http://localhost:1234/api/user/oauth2/naver", registration.getRedirectUri());
    }

}
