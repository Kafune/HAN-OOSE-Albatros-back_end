package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;


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
        String sql = "INSERT INTO activity (routeId, userId, point, duration, distance) Values (?, ?, ?, ?, ?)";
        Activity activity = new Activity();

        try {
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            sut.setDataSource(dataSource);

            //act
            sut.addNewActivity(activity);

            //assert
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, activity.getRouteId());
            verify(preparedStatement).setInt(2, activity.getUserId());
            verify(preparedStatement).setInt(3, activity.getPoint());
            verify(preparedStatement).setLong(4, activity.getDuration());
            verify(preparedStatement).setInt(5, activity.getDistance());
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }

}
