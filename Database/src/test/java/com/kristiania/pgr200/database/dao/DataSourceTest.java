package com.kristiania.pgr200.database.dao;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceTest {

    public static void resetDB(DataSource dataSource) throws SQLException{
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        createTestDb();
    }

    public static DataSource createTestDb() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();

        return dataSource;
    }
}
