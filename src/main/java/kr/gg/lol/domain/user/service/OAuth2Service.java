package kr.gg.lol.domain.user.service;


import kr.gg.lol.domain.user.dto.OAuth2Client;
import kr.gg.lol.domain.user.dto.OAuth2TokenResponse;
import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final UserRepository userRepository;
    public List<OAuth2Client.Properties> getOAuth2ClientProperties(){
        return OAuth2Client.properties(oAuth2ClientProperties);
    }
    public OAuth2TokenResponse tokenRequest(OAuth2Client.OAuth2Provider provider, String code){
        URI tokenUri = provider.tokenRequest(code, oAuth2ClientProperties);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(tokenUri, null, OAuth2TokenResponse.class);
    }

    public OAuth2User userInfoRequest(OAuth2Client.OAuth2Provider provider, String accessToken){
        OAuth2User user = provider.userInfoRequest(accessToken, oAuth2ClientProperties);
        Optional<User> result = userRepository.findBySocialId(user.getName());
        if(result.isPresent()){
            UserDto securedUser = new UserDto(result.get());
            securedUser.setAccessToken(accessToken);
            SecurityContextHolder.getContext().setAuthentication(securedUser);
        }
        return user;
    }

    public boolean logout(UserDto userDto){
        userDto.getProvider().logout(userDto.getAccessToken(), oAuth2ClientProperties);
        return true;
    }


}
