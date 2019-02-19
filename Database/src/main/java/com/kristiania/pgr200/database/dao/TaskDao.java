package com.kristiania.pgr200.database.dao;

import com.kristiania.pgr200.database.entity.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
