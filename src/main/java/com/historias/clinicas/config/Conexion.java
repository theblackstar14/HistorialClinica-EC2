package com.historias.clinicas.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.*;

@Configuration
public class Conexion {

    @Value("${spring.datasource.url}")      private String url;
    @Value("${spring.datasource.username}") private String user;
    @Value("${spring.datasource.password}") private String pass;

    @Bean
    public DataSource dataSource() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(url);
        cfg.setUsername(user);
        cfg.setPassword(pass);
        cfg.setMaximumPoolSize(10);
        return new HikariDataSource(cfg);
    }
}
