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

    private String updateSQL = "INSERT INTO TASK (TITLE, DESCRIPTION, STATUS, FIRST_USER, SECOND_USER, THIRD_USER) VALUES (?,?,?,?,?,?)";
    private String sqlUpdateTitle = "UPDATE TASK SET TITLE = ? WHERE ID = ?";
    private String sqlUpdateDesc = "UPDATE TASK SET DESCRIPTION = ? WHERE ID = ?";
    private String sqlUpdateStatus = "UPDATE TASK SET STATUS = ? WHERE ID = ?";
    private String sqlUpdateFirstU = "UPDATE TASK SET FIRST_USER = ? WHERE ID = ?";
    private String sqlUpdateSecondU = "UPDATE TASK SET SECOND_USER = ? WHERE ID = ?";
    private String sqlUpdateThirdU = "UPDATE TASK SET THIRD_USER = ? WHERE ID = ?";
    private String sqlGet = "SELECT * FROM TASK WHERE ID = ";
    private String sqlGetAll = "SELECT * FROM TASK";

    public TaskDao(DataSource dataSource){
        super(dataSource);
    }

    public List<Task> getAll() throws SQLException {
        return list(sqlGetAll, this::mapToTask);
    }

    public Task get(int id) throws SQLException {
        return get(sqlGet, this::mapToTask, id);
    }

    public void save(Task task) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement(updateSQL, PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1, task.getTitle());
                statement.setString(2, task.getDescription());
                statement.setString(3, task.getStatus());
                statement.setString(4, task.getFirstUser());
                statement.setString(5, task.getSecondUser());
                statement.setString(6, task.getThirdUser());
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
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        return task;
    }

    private void addToStatement(int id, String task, String sqlUpdate) throws SQLException{
        try (Connection connection = dataSource.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {
                statement.setString(1, task);
                statement.setLong(2,id);
                statement.executeUpdate();
            }
        }
    }

    public List<ProjectFormatted> getAllProjectsFormatted() throws SQLException {
        String sql = "SELECT id, title, description, status, first_user, second_user, third_user FROM task";
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
    public void updateTitle(int id, String title) throws SQLException {
        addToStatement(id, title, sqlUpdateTitle);
    }
    public void updateDesc(int id, String desc) throws SQLException{
        addToStatement(id, desc, sqlUpdateDesc);
    }
    public void updateStatus(int id, String status) throws SQLException{
        addToStatement(id, status, sqlUpdateStatus);
    }
    public void updateFirstU(int id, String firstUser) throws SQLException{
        addToStatement(id, firstUser, sqlUpdateFirstU);
    }
    public void updateSecondU(int id, String secondUser) throws SQLException{
        addToStatement(id, secondUser, sqlUpdateSecondU);
    }
    public void updateThirdU(int id, String thirdUser) throws SQLException{
        addToStatement(id, thirdUser, sqlUpdateThirdU);
    }
}
