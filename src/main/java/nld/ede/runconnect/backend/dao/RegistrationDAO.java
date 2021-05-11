package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO implements IRegistrationDAO {

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    @Override
    public User findUser(String googleId) throws SQLException {
        String sql = "SELECT * FROM User WHERE GOOGLE_ID_HASH = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, googleId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException exception) {
            throw exception;
        }
        return null;
    }

    @Override
    public boolean registerUser(User user) throws SQLException {
        if (!isExistingUser(user)) {
            String sql = "insert into User (FIRSTNAME, LASTNAME, E_MAILADRES, USERNAME, GOOGLE_ID_HASH, PHOTOURL) values (?, ?, ?, ?, ?, ?)";
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, user.getFirstname());
                statement.setString(2, user.getLastname());
                statement.setString(3, user.getEmailAddress());
                statement.setString(4, user.getUsername());
                statement.setString(5, user.getGoogleId());
                statement.setString(6, user.getAfbeeldingUrl());
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
        String sql = "SELECT E_MAILADRES FROM User where GOOGLE_ID_HASH = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getGoogleId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equals(user.getEmailAddress())) {
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
        user.setFirstname(rs.getString(2));
        user.setLastname(rs.getString(3));
        user.setEmailAddress(rs.getString(4));
        user.setUsername(rs.getString(5));
        user.setTotalScore(rs.getInt(6));
        user.setGoogleId(rs.getString(7));
        user.setAfbeeldingUrl(rs.getString(8));
        return user;
    }

    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
