package kr.gg.lol.domain.user.oauth.service;

import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.OAuth2Provider;
import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2Factory;
import kr.gg.lol.domain.user.oauth.factory.SimpleOAuth2FactoryImpl;
import kr.gg.lol.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static kr.gg.lol.common.constant.OAuth2Constants.*;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        SimpleOAuth2Factory simpleOAuth2Factory = SimpleOAuth2FactoryImpl
                .createOAuth2Factory(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        OAuth2User user = simpleOAuth2Factory.createOAuth2User();

        Optional<User> result = userRepository.findBySocialId((String) user.getAttribute("id"));

        //simpleOAuth2Factory.add("access_token", userRequest.getAccessToken().getTokenValue());
        simpleOAuth2Factory.add(IS_REGISTED_USER, result.isPresent());
        simpleOAuth2Factory.add(EXPIRED_AT, userRequest.getAccessToken().getExpiresAt());


        if(result.isPresent()){
            simpleOAuth2Factory.add(NICKNAME, result.get().getNickname());
            simpleOAuth2Factory.add("userId", result.get().getId());
        }
        return user;
    }
}
