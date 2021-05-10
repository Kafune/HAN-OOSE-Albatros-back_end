package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO implements IRegistrationDAO{

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;


    @Override
    public boolean registerUser(User user) throws SQLException {
        if (!isExistingUser(user.getUserId())) {
            String sql = "insert into User values (?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, user.getUserId());
                statement.setString(2, user.getFirstname());
                statement.setString(3, user.getLastname());
                statement.setString(4, user.getEmailAddress());
                statement.setString(5, user.getUsername());
                statement.setString(6, "12");
                statement.setDate(7, user.getBirthdate());
                statement.setInt(8, user.getTotalScore());
                statement.executeUpdate();
                return true;
            } catch (SQLException exception) {
                throw exception;
            }
        }
        return false;
    }

    public boolean isExistingUser(int userId) throws SQLException {
        String sql = "SELECT count(*) AS rowcount FROM User where userId = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                return false;
            }
        } catch (SQLException exception) {
                throw exception;
        }
        return true;
    }

    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
