package kr.gg.lol.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQLDialect;

import java.sql.Driver;
import java.util.TimeZone;

@Slf4j
@Getter
public enum Database {

    MYSQL(com.mysql.cj.jdbc.Driver.class, MySQLDialect.class, "jdbc:mysql://%s?characterEncoding=utf8&allowMultiQueries=true&serverTimezone=%s&useSSL=false"){
        @Override
        protected void setupVariants(BasicDataSource dataSource) {
            String format = String.format(getUrlTemplate(), "localhost:3306/lol", TimeZone.getDefault().getID());
            dataSource.setUrl(format);
            dataSource.setUsername("root");
            dataSource.setPassword("root");
        }
    },

    H2(org.h2.Driver.class, H2Dialect.class, "jdbc:h2:%s/db/h2"){
        @Override
        protected void setupVariants(BasicDataSource dataSource) {
            dataSource.setUrl(getUrlTemplate());
            dataSource.setUsername("admin");
            dataSource.setPassword("admin");
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

    protected abstract void setupVariants(BasicDataSource dataSource);

    public static Database getDatabase(String[] profiles){
        for(String profile : profiles){
            if(profile.equals("test"))
                return H2;
        }
        return MYSQL;
    }

    public void setUp(BasicDataSource dataSource){
        setupVariants(dataSource);
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
