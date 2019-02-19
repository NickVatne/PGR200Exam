package com.kristiania.pgr200.database.DatabaseHandler;

import com.kristiania.pgr200.database.dataSource.DatabaseConnector;

import javax.sql.DataSource;

public class DatabaseHandler {

    // Create Database Connection
    DatabaseConnector databaseConnector = new DatabaseConnector();
    DataSource projectDb = databaseConnector.connect();

    //  DAO Inserts

}
