package kr.gg.lol.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyTest {

    @Test
    void testGetProperty() throws Exception{
        Properties properties = new Properties();
        try(InputStream inputStream = new ClassPathResource("/dev/database.conf").getInputStream()){
            properties.load(inputStream);
        }catch (IOException e){

        }
        String key = properties.getProperty("database.username");
        assertEquals("root", key);
    }
}
