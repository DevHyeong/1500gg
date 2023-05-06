package kr.gg.lol.domain.user.service;


import kr.gg.lol.domain.user.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    public List<OAuth2Client.Properties> getOAuth2ClientProperties(){
        return OAuth2Client.properties(oAuth2ClientProperties);
    }

}
