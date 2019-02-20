package com.kristiania.pgr200.database.dao;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;

public class TestDataSource {


        @Test
        public void shouldReturnCorrectTestUser() throws SQLException {
            DataSource dataSource = new DataSourceTest().createDataSource();
            assertThat(dataSource.getConnection().getMetaData().getUserName()).isEqualTo("SA");
            assertThat(dataSource.getConnection().getMetaData().getURL()).isEqualTo("jdbc:h2:mem:test");
        }
    }


