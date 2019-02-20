package com.kristiania.pgr200.database.dataSource;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.jdbc2.optional.PoolingDataSource;
import org.postgresql.util.PSQLException;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnector {
    private Properties prop = new Properties();
    private InputStream inputStream = null;
    private static Flyway flyway;

    public DatabaseConnector() {
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
        flyway = Flyway.configure().dataSource(
                prop.getProperty("dataSource.url"),
                prop.getProperty("dataSource.username"),
                prop.getProperty("dataSource.password")).load();
        flyway.isBaselineOnMigrate();

        DataSource dataSource = createDataSource(prop);
        try {
            executeConnection(dataSource, readFile("Database/src/main/resources/db/migration/V01__create_tables.sql"));
        } catch ( SQLException e ){
            e.printStackTrace();
        }

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.isBaselineOnMigrate();

        System.out.println("Connection successful!");

        return dataSource;
    }

    public static DataSource createDataSource(Properties prop){
        PGPoolingDataSource dataSource = new PoolingDataSource();
        dataSource.setURL(prop.getProperty("dataSource.url"));
        dataSource.setUser(prop.getProperty("dataSource.username"));
        dataSource.setPassword(prop.getProperty("dataSource.password"));
        return dataSource;
    }

    private static int executeConnection(DataSource data, String query) throws SQLException {
        return executeUpdate(data.getConnection(), query);
    }

    private static int executeUpdate(Connection conn, String query) throws SQLException, PSQLException {
        if (conn == null) {
            System.err.println("Connection not reached!\n" + query);
            return -1;
        }
        int r = 0;
        try {
            Statement s = conn.createStatement();
            r = s.executeUpdate(query);
            s.close();
        } catch (PSQLException ignore) {
            ignore.getErrorCode();
        }
        return r;
    }

    private static String readFile(String filePath) {
        try (FileInputStream is = new FileInputStream(new File(filePath))) {
            char[] charArray = new char[is.available()];
            while (is.available() > 0) {
                charArray[charArray.length - is.available()] = (char) is.read();
            }
            return new String(charArray);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return "";
    }

    public void resetDatabase(DataSource dataSource) throws SQLException{
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        connect();
    }
}

