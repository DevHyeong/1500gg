package kr.gg.lol.domain.user.service;

import antlr.Token;
import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.OAuth2Provider;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.oauth.model.UserAuthentication;
import kr.gg.lol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.REGISTRATION_ID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    public boolean validateNickname(String nickname){
        return userRepository.findByNickname(nickname).isEmpty();
    }

    public UserDto signIn(UserDto userDto){
        // access_token이 유효한가

        Map<String, Object> attributes = tokenProvider.getUserFromToken(userDto.getAccessToken());
        String registrationId = (String) attributes.get(REGISTRATION_ID);
        OAuth2Provider provider = OAuth2Provider.from(registrationId);
        userDto.setProvider(provider);
        userDto.setId((String) attributes.get("id"));

        User user = new User(userDto);
        UserDto securedUser = new UserDto(userRepository.save(user));
        securedUser.setAccessToken(userDto.getAccessToken());
        securedUser.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(provider.getOAuth2User(attributes)));
        return securedUser;
    }

    public void logout(){
        UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User) userAuthentication.getPrincipal();
        String registrationId = (String) oAuth2User.getAttributes().get(REGISTRATION_ID);
        OAuth2Provider oAuth2Provider = OAuth2Provider.from(registrationId);
        oAuth2Provider.logout(userAuthentication);
    }
}
