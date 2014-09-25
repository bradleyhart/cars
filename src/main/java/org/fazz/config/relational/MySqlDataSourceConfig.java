package org.fazz.config.relational;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("production")
public class MySqlDataSourceConfig implements DataSourceBean {

    @Override
    @Bean
    public DataSource dataSource() {
        throw new UnsupportedOperationException("impl me");
    }

}
