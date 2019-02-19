package com.kristiania.pgr200.database.DatabaseHandler;

import com.kristiania.pgr200.database.dao.TaskDao;
import com.kristiania.pgr200.database.dao.TaskManagerDao;
import com.kristiania.pgr200.database.dataSource.DatabaseConnector;
import com.kristiania.pgr200.database.entity.ProjectFormatted;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class DatabaseHandler {

    // Create Database Connection
    DatabaseConnector databaseConnector = new DatabaseConnector();
    DataSource projectDb = databaseConnector.connect();

    //  DAO Inserts
    TaskDao taskDao = new TaskDao(projectDb);
    TaskManagerDao taskManagerDao = new TaskManagerDao(projectDb);

    public DatabaseHandler() throws SQLException{
    }
    public String listAllProject() throws SQLException{
        List<ProjectFormatted> tasks = taskDao.getAllProjectsFormatted();
        return null;
    }
}
