package nld.ede.runconnect.backend.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        String sql = "INSERT INTO ACTIVITIES (ROUTEID, USERID, POINT, DURATION, TEMPO, DISTANCE) Values (?, ?, ?, ?, ?, ?)";

        try {
            //mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            

        } catch (Exception e) {
            fail(e);
        }
    }

}
