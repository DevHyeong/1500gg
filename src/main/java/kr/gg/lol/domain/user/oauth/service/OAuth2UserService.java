package kr.gg.lol.domain.user.oauth.service;

import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.SocialType;
import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2FactoryImpl;
import kr.gg.lol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static kr.gg.lol.common.constant.OAuth2Constants.*;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2User user = SimpleOAuth2FactoryImpl.createOAuth2User(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        Optional<User> result = userRepository.findBySocialId((String) user.getAttribute("id"));

        //simpleOAuth2Factory.add("access_token", userRequest.getAccessToken().getTokenValue());
        user.getAttributes().put(IS_USER_REGISTED, result.isPresent());
        user.getAttributes().put(EXPIRED_AT, userRequest.getAccessToken().getExpiresAt());

        if(result.isPresent()){
            user.getAttributes().put(NICKNAME, result.get().getNickname());
            user.getAttributes().put(USER_IO, result.get().getId());
        }
        return user;
    }
}
