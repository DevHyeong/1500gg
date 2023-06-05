package kr.gg.lol.domain.user.service;

import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2FactoryImpl;
import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.oauth.model.UserAuthentication;
import kr.gg.lol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    public boolean validateNickname(String nickname){
        return userRepository.findByNickname(nickname).isEmpty();
    }

    public UserDto signIn(UserDto userDto){
        Map<String, Object> attributes = tokenProvider.getUserFromToken(userDto.getAccessToken());
        OAuth2User oAuth2User = SimpleOAuth2FactoryImpl.createOAuth2User(attributes);
        userDto.setSocialType((SocialType) oAuth2User.getAttribute("socialType"));
        userDto.setSocialId((String)oAuth2User.getAttribute("id"));

        User user = new User(userDto);
        UserDto securedUser = new UserDto(userRepository.save(user));
        securedUser.setAccessToken(userDto.getAccessToken());
        securedUser.setAuthenticated(true);
        //securedUser.setExpiredAt((Date) attributes.get("expires_at"));
        //        .queryParam("access_token", jwtToken)
        return securedUser;
    }

    public void logout(){
        UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
