package kr.gg.lol.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    private final ConfigurableEnvironment env;

    @Bean(name = "dataSource")
    public BasicDataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        Database database = Database.getDatabase(env.getActiveProfiles());
        database.setUp(dataSource);
        return dataSource;
    }

}
