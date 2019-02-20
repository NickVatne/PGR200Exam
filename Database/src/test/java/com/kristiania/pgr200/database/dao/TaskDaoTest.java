package com.kristiania.pgr200.database.dao;

import com.kristiania.pgr200.database.entity.Task;
import org.assertj.core.api.AssertionsForClassTypes;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;

public class TaskDaoTest {

    @Before
    public void init() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void shouldListAllTasks() throws SQLException {
        TaskDao dao1 = new TaskDao(DataSourceTest.createDataSource());

        List<Task> talks = sampleTasks();

        for (Task talk : talks) {
            dao1.save(talk);
        }

        assertEquals(3, dao1.getAll().size());
    }

    @Test
    public void getTaskById() throws SQLException {
        TaskDao dao1 = new TaskDao(DataSourceTest.createDataSource());

        List<Task> talks = sampleTasks();

        for (Task talk : talks) {
            dao1.save(talk);
        }

        AssertionsForClassTypes.assertThat(dao1.get(1))
                .extracting(t -> t.getTitle())
                .isEqualTo("Database 202");
    }

    @Test
    public void updateTitleTask() throws SQLException {
        TaskDao dao1 = new TaskDao(DataSourceTest.createDataSource());

        Task task1 = sampleTask();

        dao1.save(task1);

        dao1.updateTitle(1, "Infrastructure");

        AssertionsForClassTypes.assertThat(dao1.get(1))
                .extracting(t -> t.getTitle())
                .isEqualTo("Infrastructure");
    }


    @Test
    public void updateDescTask() throws SQLException {
        TaskDao dao1 = new TaskDao(DataSourceTest.createDataSource());

        Task task = sampleTask();

        dao1.save(task);

        dao1.updateDesc(1, null);

        assertThat(dao1.get(1))
                .extracting(t -> t.getDescription())
                .isEqualTo(null);
    }

    private Task sampleTask() {
        Task task = new Task();
        task.setTitle("Java 200");
        task.setDescription("Short description");
        task.setStatus("DELAYED");
        task.setFirstUser("John");
        task.setSecondUser("Mary");
        task.setThirdUser("Ben");

        return task;
    }

    private List<Task> sampleTasks() {
        Task task1 = new Task("Database 202", "Structure database", "Starting", "Jacob", "Roger", "Bill");
        Task task2 = new Task("Java 304", "Solve problem", "Delayed", "Billy", "Kate", "Ross");
        Task task3 = new Task("JavaScript 6", "Frontpage", "Finished", "Chandler", "Rachel", "Macy");

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        return tasks;
    }
}
