package com.kristiania.pgr200.database.DatabaseHandler;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.jdbc2.optional.PoolingDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHandler {
    private Properties prop = new Properties();
    private InputStream inputStream = null;

    public DatabaseHandler() {
    }
    public DataSource connect() {
        try {
            String fileName = "innlevering.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            prop.load(inputStream);
        } catch ( IOException e ) {
            e.printStackTrace();
            System.out.println("Can´t find Properties file");
        } finally {
            if (inputStream != null){
                try {inputStream.close();
            } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        PGPoolingDataSource dataSource = new PoolingDataSource();
        dataSource.setURL(prop.getProperty("dataSource.url"));
        dataSource.setUser(prop.getProperty("dataSource.username"));
        dataSource.setPassword(prop.getProperty("dataSource.password"));

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();

        System.out.println("Database Connected Successfully");

        return dataSource;
    }
    public void resetDatabase(DataSource dataSource) throws SQLException{
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        connect();
    }
}
