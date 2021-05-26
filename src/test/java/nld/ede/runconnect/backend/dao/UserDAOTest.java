package nld.ede.runconnect.backend.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class UserDAOTest
{
    DataSource dataSource;
    Connection connection;
    PreparedStatement preparedStatement;
    UserDAO userDAO;

    @BeforeEach
    public void setData() {
        userDAO = new UserDAO();
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        userDAO.setDatasource(dataSource);
    }

    @Test
    public void searchForUsersTest() {
        String sql = "SELECT * FROM USER WHERE username LIKE ?";

        try {
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            UserDAO userDAOSpy = spy(UserDAO.class);

            // Setup mocks.
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            userDAOSpy.setDatasource(dataSource);

            // Act
            userDAOSpy.searchForUsers("Henk");

            // Assert
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, "Henk%");
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e);
        }
    }
}
