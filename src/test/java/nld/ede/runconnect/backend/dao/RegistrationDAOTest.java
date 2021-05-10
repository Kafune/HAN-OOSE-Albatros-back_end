package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class RegistrationDAOTest {

    private RegistrationDAO sut;

    @BeforeEach
    public void setup() {
        sut = new RegistrationDAO();
    }

    @Test
    public void registerUserReturnsTrueIfUserAddedTest() {
        int userId = 3;
        User user = new User();
        user.setUserId(userId);
        String sql = "insert into User values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            RegistrationDAO sutSpy = spy(sut);
            doReturn(false).when(sutSpy).isExistingUser(userId);

            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);


            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

            sutSpy.setDatasource(dataSource);
            boolean isRegistered = sutSpy.registerUser(user);

            verify(connection).prepareStatement(sql);
            verify(preparedStatement).executeUpdate();
            assertTrue(isRegistered);
        } catch (SQLException e) {
            fail();
        }
    }
    @Test
    public void registerUserReturnsFalseIfUserExistsTest() {
        int userId = 3;
        User user = new User();
        user.setUserId(userId);
        try {
            RegistrationDAO sutSpy = spy(sut);
            doReturn(true).when(sutSpy).isExistingUser(userId);

            boolean isRegistered = sutSpy.registerUser(user);

            assertFalse(isRegistered);

        } catch (SQLException e) {
            fail();
        }
    }
    @Test
    public void isExistingUserReturnsTrueIfUserFoundTest() {
        int userId = 3;
        User user = new User();
        user.setUserId(userId);
        String sql = "SELECT count(*) AS rowcount FROM User where userId = ?";
        try {

            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            ResultSet resultSet = mock(ResultSet.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);


            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.getInt(1)).thenReturn(3);

            sut.setDatasource(dataSource);
            boolean exist = sut.isExistingUser(userId);

            verify(connection).prepareStatement(sql);
            verify(preparedStatement).executeQuery();
            verify(resultSet).next();
            assertTrue(exist);
        } catch (SQLException e) {
            fail();
        }
    }
    @Test
    public void isExistingUserReturnsFalseIfUserNotFoundTest() {
        int userId = 3;
        User user = new User();
        user.setUserId(userId);
        String sql = "SELECT count(*) AS rowcount FROM User where userId = ?";
        try {

            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            ResultSet resultSet = mock(ResultSet.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);


            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.getInt(1)).thenReturn(0);

            sut.setDatasource(dataSource);
            boolean exist = sut.isExistingUser(userId);

            verify(connection).prepareStatement(sql);
            verify(preparedStatement).executeQuery();
            verify(resultSet).next();
            assertFalse(exist);
        } catch (SQLException e) {
            fail();
        }
    }
}
