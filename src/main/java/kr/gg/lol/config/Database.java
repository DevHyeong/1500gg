package kr.gg.lol.config;

import kr.gg.lol.common.constant.DatabaseConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.MySQLDialect;

import java.sql.Driver;
import java.util.Properties;
import java.util.TimeZone;

import static kr.gg.lol.common.constant.DatabaseConstants.*;

@Slf4j
@Getter
public enum Database {

    MYSQL(com.mysql.cj.jdbc.Driver.class, MySQLDialect.class, "jdbc:mysql://%s?characterEncoding=utf8&allowMultiQueries=true&serverTimezone=%s&useSSL=false"){
        @Override
        protected void setupVariants(BasicDataSource dataSource, Properties properties) {
            String format = String.format(getUrlTemplate(), properties.getProperty(DATABASE_URL), TimeZone.getDefault().getID());
            dataSource.setUrl(format);
            dataSource.setUsername(properties.getProperty(DATABASE_USERNAME));
            dataSource.setPassword(properties.getProperty(DATABASE_PASSWORD));
        }
    },

    H2(org.h2.Driver.class, H2Dialect.class, ""){//"jdbc:h2:tcp:/db/h2"
        @Override
        protected void setupVariants(BasicDataSource dataSource, Properties properties) {
            dataSource.setUrl(properties.getProperty(DATABASE_URL));
            dataSource.setUsername(properties.getProperty(DATABASE_USERNAME));
            dataSource.setPassword(properties.getProperty(DATABASE_PASSWORD));
        }
    };

    private static final int DB_MAX_OPEN_PREPARED_STATEMENTS = 50;
    private static final int DB_MAX_WAIT = 3000;
    private static final int DB_MIN_IDLE = 5;
    private static final int DB_MAX_ACTIVE = 20;
    private static final int DB_INITIAL_SIZE = 5;
    private final String urlTemplate;
    private final String jdbcDriverName;
    private final String dialect;

    Database(Class<? extends Driver> jdbcDriver, Class<? extends Dialect> dialect, String urlTemplate){
        this.jdbcDriverName = jdbcDriver.getCanonicalName();
        this.dialect = dialect.getCanonicalName();
        this.urlTemplate = urlTemplate;
    }

    protected abstract void setupVariants(BasicDataSource dataSource, Properties properties);

    public static Database getDatabase(String value){
        return Database.valueOf(value);
    }

    public void setUp(BasicDataSource dataSource, Properties properties){
        setupVariants(dataSource, properties);
        setupCommon(dataSource);
    }

    protected void setupCommon(BasicDataSource dataSource) {
        dataSource.setDriverClassName(getJdbcDriverName());
        dataSource.setInitialSize(DB_INITIAL_SIZE);
        dataSource.setMaxActive(DB_MAX_ACTIVE);
        dataSource.setMinIdle(DB_MIN_IDLE);
        dataSource.setMaxWait(DB_MAX_WAIT);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(DB_MAX_OPEN_PREPARED_STATEMENTS);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setValidationQuery("SELECT 1");
    }
}
