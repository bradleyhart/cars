package org.fazz.config.persistence.relational;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("development")
public class InMemoryDataSourceConfig implements DataSourceBean {

    @Bean
    @Override
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:cars;MODE=MySQL");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

}
