package kr.gg.lol.common.util;

import kr.gg.lol.domain.summoner.dto.SummonerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;

@Component
public class Rest {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${lol.api.key}")
    private String key;

    @PostConstruct
    public void init(){

    }

    public <T> ResponseEntity<T> get(URI uri, Class<T> responseType){

        RequestEntity requestEntity = RequestEntity.get(uri)
                .header("X-Riot-Token", key)
                .build();

        try{

            ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET,
                    requestEntity, responseType);

            return response;
        }catch (HttpClientErrorException e){
            throw new RuntimeException("");
        }
    }

    public <T> ResponseEntity<T> get(URI uri, ParameterizedTypeReference<T> responseType){

        RequestEntity requestEntity = RequestEntity.get(uri)
                .header("X-Riot-Token", key)
                .build();
        try{

            ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET,
                    requestEntity, responseType);

            return response;
        }catch (HttpClientErrorException e){
            throw new RuntimeException("");
        }
    }


}
