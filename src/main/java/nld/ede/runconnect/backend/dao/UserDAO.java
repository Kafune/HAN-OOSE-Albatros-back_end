package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static nld.ede.runconnect.backend.dao.helpers.ConnectionHandler.close;

public class UserDAO implements IUserDAO
{
    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    /**
     * Searches for users by search value.
     * @param searchValue The value to search for.
     * @return The found users, if any.
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public ArrayList<User> searchForUsers(String searchValue) throws SQLException {
        ArrayList<User> foundUsers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String searchUsersQuery = "SELECT * FROM USER WHERE username LIKE ?";
            statement = connection.prepareStatement(searchUsersQuery);
            statement.setString(1, searchValue + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                foundUsers.add(registrationDAO.extractUser(resultSet));
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
        return foundUsers;
    }

    /**
     * Follow or unfollow a user based on follower and followee ID.
     * This method only contains the business logic.
     * @param wantsToFollow Boolean value, if true follow, if false unfollow.
     * @param followerId The ID of the person trying to follow a user.
     * @param followeeId The ID of the user to follow.
     * @return If the method was executed successful.
     */
    public boolean toggleFollow(boolean wantsToFollow, int followerId, int followeeId) throws SQLException
    {
        // If user is already following or already not following user, return out of method.
        if (isFollowing(followerId, followeeId) == wantsToFollow) {
            return false;
        }

        String query;

        if (wantsToFollow) {
            query = "INSERT INTO FOLLOWS (FOLLOWERID, FOLLOWEEID) VALUES (?, ?)";
        } else {
            query = "DELETE FROM FOLLOWS WHERE FOLLOWERID = ? AND FOLLOWEEID = ?";
        }

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, followerId);
            statement.setInt(2, followeeId);

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
    }

    /**
     * Checks if user is already following other user.
     * @param followerId The ID of the follower.
     * @param followeeId The ID of the followee.
     * @return If the follower is already following the followee.
     * @throws SQLException Exception if SQL fails.
     */
    public boolean isFollowing(int followerId, int followeeId) throws SQLException
    {
        String query = "SELECT * FROM FOLLOWS WHERE FOLLOWERID = ? AND FOLLOWEEID = ?";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, followerId);
            statement.setInt(2, followeeId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }

        return false;
    }

    /**
     * Sets datasource.
     * @param dataSource the data source
     */
    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
