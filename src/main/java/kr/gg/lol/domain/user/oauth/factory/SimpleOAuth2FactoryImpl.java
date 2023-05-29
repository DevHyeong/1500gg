package kr.gg.lol.domain.user.oauth.factory;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.REGISTRATION_ID;

public class SimpleOAuth2FactoryImpl {

    public static SimpleOAuth2Factory createOAuth2Factory(Map<String, Object> attributes){
        String registrationId = (String) attributes.get(REGISTRATION_ID);
        return createOAuth2Factory(registrationId, attributes);
    }

    public static SimpleOAuth2Factory createOAuth2Factory(String registrationId, Map<String ,Object> attributes){
        if(registrationId.equals("naver")){
            Map<String, Object> map = (Map<String, Object>) attributes.get("response");
            return new NaverOAuth2Factory(map == null ? attributes : map);
        }
        throw new IllegalArgumentException();
    }
}
