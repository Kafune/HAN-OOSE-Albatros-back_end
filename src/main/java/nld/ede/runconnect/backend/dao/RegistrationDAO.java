package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static nld.ede.runconnect.backend.dao.helpers.ConnectionHandler.close;

public class RegistrationDAO implements IRegistrationDAO {

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public User findUser(String email) throws SQLException {
        String sql = "SELECT * FROM `USER` WHERE E_MAILADRES = ?";
        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return extractUser(resultSet);
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
        return null;
    }

    @Override
    public boolean registerUser(User user) throws SQLException {
        if (!isExistingUser(user)) {
            String sql = "insert into `USER` (FIRSTNAME, LASTNAME, E_MAILADRES, USERNAME, IMAGE_URL) values (?, ?, ?, ?, ?)";
            try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmailAddress());
                statement.setString(4, user.getUsername());
                statement.setString(5, user.getImageUrl());
                statement.executeUpdate();
                return true;
            } catch (SQLException exception) {
                exception.printStackTrace();
                throw exception;
            }
        }
        return false;
    }

    public boolean isExistingUser(User user) throws SQLException {
        String sql = "SELECT count(*) as count FROM `USER` where E_MAILADRES = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmailAddress());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
        return false;
    }

    public User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt(1));
        user.setFirstName(rs.getString(2));
        user.setLastName(rs.getString(3));
        user.setEmailAddress(rs.getString(4));
        user.setUsername(rs.getString(5));
        user.setTotalScore(rs.getInt(6));
        user.setImageUrl(rs.getString(7));
        return user;
    }

    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
