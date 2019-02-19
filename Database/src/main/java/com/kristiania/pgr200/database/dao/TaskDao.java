package com.kristiania.pgr200.database.dao;

import com.kristiania.pgr200.database.entity.ProjectFormatted;
import com.kristiania.pgr200.database.entity.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao extends AbstractDao {

    public TaskDao(DataSource dataSource){
        super(dataSource);
    }
    public List<Task> getAll() throws SQLException {
        return list("SELECT * FROM TASK", this::mapToTask);
    }
    public void save(Task task) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            String sql = "INSERT INTO TASK (TASK) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1,task.getTitle());

                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()){
                    resultSet.next();
                    task.setId(resultSet.getInt(1));
                }
            }
        }
    }
    private Task mapToTask(ResultSet rs) throws SQLException{
        Task task = new Task();
        task.setId(rs.getInt("ID"));
        task.setTitle(rs.getString("TASK"));

        return task;
    }
    public List<ProjectFormatted> getAllProjectsFormatted() throws SQLException {
        String sql = "SELECT ta.id, ta title, ta.description, ta.status, tm.first_user, tm.second_user, tm.third_user from TASK ta"
                + "INNER JOIN TASKMANAGER tm on (tm.id = ta.taskmanager_id)";
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<ProjectFormatted> result = new ArrayList<>();
                    while (rs.next()) {
                        ProjectFormatted projectList = new ProjectFormatted(rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getString("status"),
                                rs.getString("first_user"),
                                rs.getString("second_user"),
                                rs.getString("third_user"));
                        result.add(projectList);
                    }
                    return result;
                }

            }
        }
    }
}