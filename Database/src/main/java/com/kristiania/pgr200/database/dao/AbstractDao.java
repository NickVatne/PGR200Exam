package com.kristiania.pgr200.database.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDao {

    protected final DataSource dataSource;
    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected <T> List<T> list(String sql, ResultSetMapper<T> mapper) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                try (ResultSet resultset = statement.executeQuery()) {
                    List<T> result = new ArrayList<>();
                    while (resultset.next()) {
                        result.add(mapper.mapResultSet(resultset));
                    }
                    return result;
                }
            }
        }
    }

    protected <T> T get(String sql, ResultSetMapper<T> mapper, int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql + id)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        return mapper.mapResultSet(rs);
                    }
                }
            }
            return null;
        }
    }
}
