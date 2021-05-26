package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO implements IRegistrationDAO {

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    /**
     * Finds a user based on given email.
     * @param email Email to look for.
     * @return the found user (if found).
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public User findUser(String email) throws SQLException {
        String sql = "SELECT * FROM `USER` WHERE E_MAILADRES = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException exception) {
            throw exception;
        }
        return null;
    }

    /**
     * Registers a user.
     * @param user The user to register
     * @return If the register was successful.
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public boolean registerUser(User user) throws SQLException {
        if (!isExistingUser(user)) {
            String sql = "insert into `USER` (FIRSTNAME, LASTNAME, E_MAILADRES, USERNAME, IMAGE_URL) values (?, ?, ?, ?, ?)";
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql);
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

    /**
     * Checks if a user exists in the database.
     * @param user The user to check.
     * @return If the user exists
     * @throws SQLException Exception if SQL fails.
     */
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

    /**
     * Extracts a user from a result set.
     * @param rs The result set.
     * @return The extracted user.
     * @throws SQLException Exception if SQL fails.
     */
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

    /**
     * Sets datasource.
     * @param dataSource the data source
     */
    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
