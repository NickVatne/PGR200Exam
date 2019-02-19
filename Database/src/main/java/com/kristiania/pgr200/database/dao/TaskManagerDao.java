package com.kristiania.pgr200.database.dao;

import com.kristiania.pgr200.database.entity.TaskManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TaskManagerDao extends AbstractDao {
    private String sqlGET = "SELECT * FROM TASKMANAGER";
    private String sqlSave = "INSERT INTO TASKMANAGER (FIRST_USER, SECOND_USER, THIRD_USER) VALUES (?, ?, ?)";

    public TaskManagerDao(DataSource dataSource){
        super(dataSource);
    }
    public List<TaskManager> getAll () throws SQLException{
        return list(sqlGET, this::mapToManager);
    }

    public void save(TaskManager taskManager) throws SQLException{
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement(sqlSave, PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1, taskManager.getFirstUser());
                statement.setString(2, taskManager.getSecondUser());
                statement.setString(3, taskManager.getThirdUser());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()){
                    resultSet.next();
                    taskManager.setId(resultSet.getInt(1));
                }
            }
        }
    }

    private TaskManager mapToManager (ResultSet rs) throws SQLException{
        TaskManager taskManager = new TaskManager();
        taskManager.setId(rs.getInt("ID"));
        taskManager.setFirstUser(rs.getString("FIRST_USER"));
        taskManager.setFirstUser(rs.getString("SECOND_USER"));
        taskManager.setFirstUser(rs.getString("THIRD_USER"));

        return taskManager;

    }
}
