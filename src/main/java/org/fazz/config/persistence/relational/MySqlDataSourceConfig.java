package org.fazz.config.persistence.relational;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("production")
public class MySqlDataSourceConfig implements DataSourceBean {

    @Bean
    @Override
    public DataSource dataSource() {
        return new DriverManagerDataSource() {{
            setDriverClassName("com.mysql.jdbc.Driver");
            setUrl("jdbc:mysql://localhost/cars");
            setUsername("root");
            setPassword("password");
        }};
    }

}
