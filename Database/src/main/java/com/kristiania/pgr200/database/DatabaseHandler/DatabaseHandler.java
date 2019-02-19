package com.kristiania.pgr200.database.DatabaseHandler;

import com.kristiania.pgr200.database.dao.TaskDao;
import com.kristiania.pgr200.database.dao.TaskManagerDao;
import com.kristiania.pgr200.database.dataSource.DatabaseConnector;
import com.kristiania.pgr200.database.entity.ProjectFormatted;
import com.kristiania.pgr200.database.entity.Task;
import com.kristiania.pgr200.database.entity.TaskManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {

    // Create Database Connection
    DatabaseConnector databaseConnector = new DatabaseConnector();
    DataSource projectDb = databaseConnector.connect();

    //  DAO Inserts
    TaskDao taskDao = new TaskDao(projectDb);
    TaskManagerDao taskManagerDao = new TaskManagerDao(projectDb);

    public DatabaseHandler() throws SQLException {
    }

    public String listAllProject() throws SQLException {
        List<ProjectFormatted> tasks = taskDao.getAllProjectsFormatted();

        if (tasks.isEmpty()) {
            System.out.println("There is no Projects, Please Add One");
        }
        StringBuilder response = new StringBuilder();
        response.append(ProjectFormatted.createHeaderLine());
        for (ProjectFormatted project : tasks) {
            response.append(project.formatOutput());
        }
        return response.toString();
    }

    public void insertProject(Map<String, String> parameters) throws SQLException {
        Task tempTask = new Task();

        String title = parameters.get("TITLE");
        String desc = parameters.get("DESCRIPTION");
        String status = parameters.get("STATUS");
        int taskmanager = Integer.parseInt(parameters.get("FIRST_USER"));
        int taskmanager2 = Integer.parseInt(parameters.get("SECOND_USER"));
        int taskmanager3 = Integer.parseInt(parameters.get("THIRD_USER"));


        tempTask.setTitle(title);
        tempTask.setDescription(desc);
        tempTask.setStatus(status);
        tempTask.setTimemanagerId(taskmanager);
        tempTask.setTimemanagerId(taskmanager2);
        tempTask.setTimemanagerId(taskmanager3);

        taskDao.save(tempTask);
    }

    public void updateTaskTitle(Map<String, String> parameters) throws SQLException {
        int id = Integer.parseInt(parameters.get("id"));
        String title = parameters.get("title");

        taskDao.updateTitle(id, title);
    }

    public void updateTaskDesc(Map<String, String> parameters) throws SQLException {
        int id = Integer.parseInt(parameters.get("id"));
        String desc = parameters.get("description");

        taskDao.updateDesc(id, desc);
    }

    public void updateTaskStatus(Map<String, String> parameteres) throws SQLException {
        int id = Integer.parseInt(parameteres.get("id"));
        String status = parameteres.get("status");

        taskDao.updateStatus(id, status);
    }

    public List<Task> getAllAvailableTasks() throws SQLException {
        return taskDao.getAll();
    }

    public List<TaskManager> getAllAvailableTaskManagers() throws SQLException {
        return taskManagerDao.getAll();
    }

}

