package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationDAO implements IRegistrationDAO{

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;


    @Override
    public void registerUser(User user) throws SQLException {
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
            statement.setInt(8,user.getTotalScore());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        }
    }
}
