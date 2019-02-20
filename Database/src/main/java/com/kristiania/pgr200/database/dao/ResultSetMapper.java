package com.kristiania.pgr200.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface

public interface ResultSetMapper<T> { T mapResultSet(ResultSet rs) throws SQLException; }