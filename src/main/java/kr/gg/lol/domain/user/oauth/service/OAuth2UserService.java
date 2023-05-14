package kr.gg.lol.domain.user.oauth.service;

import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.oauth.enums.OAuth2Provider;
import kr.gg.lol.domain.user.oauth.model.NaverOAuth2User;
import kr.gg.lol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static kr.gg.lol.common.constant.OAuth2Constants.EXPIRED_AT;
import static kr.gg.lol.common.constant.OAuth2Constants.IS_REGISTED_USER;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    //AAAAN1hD0ln8_jNNhn1Y3s1S0TBwJS8LSGkjGHuFFhuqkcb4D3XjYqnsKwps74K7yPSdig7pFNFWo2jH5q8k0T4Jl9s
    @Autowired
    private UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Provider oAuth2Provider = OAuth2Provider.from(userRequest.getClientRegistration().getRegistrationId());
        OAuth2User user = oAuth2Provider.getOAuth2User(oAuth2User.getAttributes());
        user.getAttributes().put(EXPIRED_AT, userRequest.getAccessToken().getExpiresAt());

        Optional<User> result = userRepository.findBySocialId(user.getName());
        user.getAttributes().put(IS_REGISTED_USER, result.isPresent());
        if(result.isPresent())
            user.getAttributes().put("nickname", result.get().getNickname());

        return user;
    }



}
