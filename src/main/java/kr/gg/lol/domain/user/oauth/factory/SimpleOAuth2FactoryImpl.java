package kr.gg.lol.domain.user.oauth.factory;

import kr.gg.lol.domain.user.oauth.enums.SocialType;
import kr.gg.lol.domain.user.oauth.model.KakaoOAuth2User;
import kr.gg.lol.domain.user.oauth.model.NaverOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

import static kr.gg.lol.common.constant.OAuth2Constants.REGISTRATION_ID;

public class SimpleOAuth2FactoryImpl {

    public static OAuth2User createOAuth2User(Map<String, Object> attributes){
        String registrationId = (String) attributes.get(REGISTRATION_ID);
        return createOAuth2User(registrationId, attributes);
    }

    public static OAuth2User createOAuth2User(String registrationId, Map<String ,Object> attributes){
        if(registrationId.equals(SocialType.NAVER.getValue())){
            Map<String, Object> map = (Map<String, Object>) attributes.get("response");
            return new NaverOAuth2User(map == null ? attributes : map);
        }
        if(registrationId.equals(SocialType.KAKAO.getValue())){
            Map<String, Object> map = new HashMap<>();
            attributes.forEach((k,v) -> {
                if(k.equals("id") && v instanceof Long){
                    Long id = (Long) v;
                    map.put(k, String.valueOf(id));
                }else{
                    map.put(k, v);
                }
            });

            return new KakaoOAuth2User(map);
        }
        throw new IllegalArgumentException();
    }
}
