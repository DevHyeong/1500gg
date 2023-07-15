package kr.gg.lol.common.util;

import kr.gg.lol.common.ConfigFileProperties;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class RiotApi {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConfigFileProperties configFileProperties;
    private final String key;
    public RiotApi(ConfigFileProperties configFileProperties) {
        this.configFileProperties = configFileProperties;
        this.key = configFileProperties.getProperty("lol.api.key");
    }

    public <T> ResponseEntity<T> getWithToken(URI uri, Class<T> responseType){
        RequestEntity requestEntity = RequestEntity.get(uri)
                .header("X-Riot-Token", key)
                .build();
        try{
            ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET,
                    requestEntity, responseType);
            return response;
        }catch (HttpClientErrorException e){
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }

    public <T> ResponseEntity<T> getWithToken(URI uri, ParameterizedTypeReference<T> responseType){
        RequestEntity requestEntity = RequestEntity.get(uri)
                .header("X-Riot-Token", key)
                .build();
        try{
            ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET,
                    requestEntity, responseType);
            return response;
        }catch (HttpClientErrorException e){
            throw new RuntimeException(e);
        }
    }
}
