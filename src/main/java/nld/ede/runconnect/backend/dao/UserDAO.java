package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements IUserDAO
{
    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    /**
     * Searches for users by search value.
     * @param searchValue The value to search for.
     * @return The found users, if any.
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public ArrayList<User> searchForUsers(String searchValue) throws SQLException
    {
        ArrayList<User> foundUsers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String searchUsersQuery = "SELECT * FROM USER WHERE username LIKE ?";
            PreparedStatement statement = connection.prepareStatement(searchUsersQuery);
            statement.setString(1, searchValue + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                foundUsers.add(registrationDAO.extractUser(resultSet));
            }
        } catch (SQLException exception) {
            throw exception;
        }
        return foundUsers;
    }

    /**
     * Sets datasource.
     * @param dataSource the data source
     */
    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
