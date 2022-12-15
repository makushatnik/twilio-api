package com.example.myproj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBPopulator {

    private final DataSource dataSource;

    @Value("${app.dbPopulator.enabled}")
    private boolean enabled;

    @PostConstruct
    public void start() {
        if (!enabled) return;

        ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
        rdp.addScript(new ClassPathResource("db/populateDB.sql"));
        try {
            Connection connection = dataSource.getConnection();
            rdp.populate(connection);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
