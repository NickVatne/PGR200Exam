package com.kristiania.pgr200.database.dao;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceTest {

    public  void resetDB(DataSource dataSource) throws SQLException{
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        createDataSource();
    }

    public static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");

        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
    public static DataSource createTempDataSource(){
        return createDataSource();
    }
}
