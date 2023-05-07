package kr.gg.lol.domain.user.service;


import kr.gg.lol.domain.user.dto.OAuth2Client;
import kr.gg.lol.domain.user.dto.OAuth2TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    public List<OAuth2Client.Properties> getOAuth2ClientProperties(){
        return OAuth2Client.properties(oAuth2ClientProperties);
    }
    public OAuth2TokenResponse tokenRequest(String socialType, String code){
        OAuth2Client.OAuth2Provider provider = Arrays.stream(OAuth2Client.OAuth2Provider
                .values()).filter(e-> e.getProvider().equals(socialType))
                .findFirst()
                .get();
        URI tokenUri = provider.tokenRequest(code, oAuth2ClientProperties);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(tokenUri, null, OAuth2TokenResponse.class);
    }

}
