package kr.gg.lol.domain.user.oauth.factory;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public abstract class SimpleOAuth2Factory {
    protected final Map<String, Object> attributes;
    public SimpleOAuth2Factory(Map<String, Object> attributes){
        this.attributes = attributes;
    }
    public void add(String key, Object value){
        attributes.put(key, value);
    }
    public abstract OAuth2User createOAuth2User();

}
