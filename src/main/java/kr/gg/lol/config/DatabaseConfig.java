package kr.gg.lol.config;

import kr.gg.lol.common.constant.DatabaseConstants;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static kr.gg.lol.common.constant.DatabaseConstants.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {
    private final ConfigurableEnvironment env;

    @Bean(name = "dataSource")
    public BasicDataSource dataSource(){
        Properties properties = initProperties();
        BasicDataSource dataSource = new BasicDataSource();
        Database database = Database.getDatabase(properties.getProperty(DATABASE_TYPE));
        database.setUp(dataSource, properties);
        return dataSource;
    }

    private Properties initProperties(){
        Properties properties = new Properties();
        String environment = "dev";
        for(String profile : env.getActiveProfiles()){
            if(profile.equals("prod"))
                environment = "prod";
            if(profile.equals("local"))
                environment = "local";
        }
        log.info(environment);
        try(InputStream inputStream = new ClassPathResource(String.format("/%s/database.conf", environment))
                .getInputStream()){
            properties.load(inputStream);
        }catch (IOException e){
            log.error("{}", e);
        }
        return properties;
    }

}
