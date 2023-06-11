package kr.gg.lol.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ConfigFileProperties {
    private final Properties properties;
    public ConfigFileProperties() {
        properties = new Properties();
        try(InputStream inputStream = new ClassPathResource("/app.conf").getInputStream()){
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

}
