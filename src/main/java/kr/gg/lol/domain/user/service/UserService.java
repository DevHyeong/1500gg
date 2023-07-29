package kr.gg.lol.domain.user.service;

import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2FactoryImpl;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.oauth.model.UserAuthentication;
import kr.gg.lol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    public boolean validateNickname(String nickname){
        return userRepository.findByNickname(nickname).isEmpty();
    }

    public UserDto signIn(UserDto userDto){
        User user = getUserFromUserDto(userDto);
        UserDto securedUserDto = new UserDto(userRepository.save(user));
        securedUserDto.setAccessToken(regenerateAccessToken(userDto, securedUserDto));
        securedUserDto.setAuthenticated(true);
        return securedUserDto;
    }

    public void logout(){
        Authentication userAuthentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User) userAuthentication.getPrincipal();
        String token = (String) oAuth2User.getAttributes().get(ACCESS_TOKEN);
        tokenProvider.revokeToken(token);
    }

    public User getUserFromUserDto(UserDto userDto){
        Map<String, Object> attributes = tokenProvider.getUserFromToken(userDto.getAccessToken());
        OAuth2User oAuth2User = SimpleOAuth2FactoryImpl.createOAuth2User(attributes);
        userDto.setSocialTypeStr(oAuth2User.getName());
        userDto.setSocialId((String)oAuth2User.getAttribute("id"));
        return new User(userDto);
    }

    private String regenerateAccessToken(UserDto requestUserDto, UserDto savedUserDto){
        Map<String, Object> attributes = tokenProvider.getUserFromToken(requestUserDto.getAccessToken());
        OAuth2User oAuth2User = SimpleOAuth2FactoryImpl.createOAuth2User(attributes);
        oAuth2User.getAttributes().put(USER_ID, savedUserDto.getId());
        oAuth2User.getAttributes().put(NICKNAME, savedUserDto.getNickname());
        return tokenProvider.createToken(new UserAuthentication(oAuth2User));
    }
}
