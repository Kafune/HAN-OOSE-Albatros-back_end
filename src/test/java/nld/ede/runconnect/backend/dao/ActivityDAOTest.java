package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ActivityDAOTest {
    DataSource dataSource;
    Connection connection;
    PreparedStatement preparedStatement;
    ActivityDAO sut;

    @BeforeEach
    public void setSut() {
        sut = new ActivityDAO();
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        sut.setDataSource(dataSource);
    }

    @Test
    public void addNewActivityTest() {
        try {
            when(dataSource.getConnection()).thenThrow(new SQLException());
        } catch (Exception e) {
            fail(e);
        }

        sut.setDataSource(dataSource);

        //act and assert
        assertThrows(SQLException.class, () -> sut.addNewActivity(new Activity()));
    }

}
