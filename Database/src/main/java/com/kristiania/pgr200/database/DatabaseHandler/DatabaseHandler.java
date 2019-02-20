package com.kristiania.pgr200.database.DatabaseHandler;

import com.kristiania.pgr200.database.dao.TaskDao;
import com.kristiania.pgr200.database.dataSource.DatabaseConnector;
import com.kristiania.pgr200.database.entity.ProjectFormatted;
import com.kristiania.pgr200.database.entity.Task;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {

    DatabaseConnector databaseConnector = new DatabaseConnector();
    DataSource projectDb = databaseConnector.connect();

    TaskDao taskDao = new TaskDao(projectDb);

    public DatabaseHandler() throws SQLException {
    }

    public String listAllProject() throws SQLException {
        List<ProjectFormatted> tasks = taskDao.getAllProjectsFormatted();

        if (tasks.isEmpty()) {
            System.out.println("There is no Projects, Please Add One");
        }
        StringBuilder response = new StringBuilder();
        for (ProjectFormatted project : tasks) {
            response.append(project.formatOutput());
        }
        return response.toString();
    }

    public void insertProject(Map<String, String> parameters) throws SQLException {
        Task tempTask = new Task();

        String title = parameters.get("title");
        String desc = parameters.get("description");
        String status = parameters.get("status");
        String firstUser = parameters.get("first_user");
        String secondUser = parameters.get("second_user");
        String thirdUser = parameters.get("third_user");

        tempTask.setTitle(title);
        tempTask.setDescription(desc);
        tempTask.setStatus(status);
        tempTask.setFirstUser(firstUser);
        tempTask.setSecondUser(secondUser);
        tempTask.setThirdUser(thirdUser);

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

    public void updateTaskFirstU(Map<String, String> parameteres) throws SQLException {
        int id = Integer.parseInt(parameteres.get("id"));
        String firstUser = parameteres.get("first_user");

        taskDao.updateFirstU(id, firstUser);
    }

    public void updateTaskSecondU(Map<String, String> parameteres) throws SQLException {
        int id = Integer.parseInt(parameteres.get("id"));
        String secondUser = parameteres.get("second_user");

        taskDao.updateSecondU(id, secondUser);
    }

    public void updateTaskThirdU(Map<String, String> parameteres) throws SQLException {
        int id = Integer.parseInt(parameteres.get("id"));
        String thirdUser = parameteres.get("third_user");

        taskDao.updateThirdU(id, thirdUser);
    }

    public List<Task> getAllAvailableTasks() throws SQLException {
        return taskDao.getAll();
    }
}

