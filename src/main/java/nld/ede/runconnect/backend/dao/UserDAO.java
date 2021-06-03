package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static nld.ede.runconnect.backend.dao.helpers.ConnectionHandler.close;

public class UserDAO implements IUserDAO
{
    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

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
                foundUsers.add(extractUser(resultSet));
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
        return foundUsers;
    }
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
                statement = connection.prepareStatement(sql);
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmailAddress());
                statement.setString(4, user.getUsername());
                statement.setString(5, user.getImageUrl());
                statement.executeUpdate();
                return true;
            } catch (SQLException exception) {
                throw exception;
            } finally {
                close(statement, null);
            }
        }
        return false;
    }

    /**
     * Get's a user domain based on it's ID.
     *
     * @param userId The ID of the searchable user.
     * @return The user if found. Null if not found.
     * @throws SQLException Exception if SQL fails.
     */
    public User getById(int userId) throws SQLException {
        String sql = "SELECT * FROM `USER` WHERE USERID = ?";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getActivitiesFromUser(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            close(statement, resultSet);
        }

        return null;
    }
    /**
     * Gets all the following users based on the ID of the follower.
     *
     * @param userId The ID of the follower.
     * @return A list of users to follow
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public ArrayList<Integer> getFollowingUsers(int userId) throws SQLException
    {
        String sql = "SELECT * FROM FOLLOWS WHERE FOLLOWERID = ?";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            ArrayList<Integer> following = new ArrayList<>();

            while (resultSet.next()) {
                following.add(resultSet.getInt(2));
            }

            return following;
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
    }

    /**
     * Gets all activities from users and combines it to send it off.
     * @param rs resultset
     * @return user
     * @throws SQLException if an sql error occurs
     */
    public User getActivitiesFromUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt(1));
        user.setFirstName(rs.getString(2));
        user.setLastName(rs.getString(3));
        user.setEmailAddress(rs.getString(4));
        user.setUsername(rs.getString(5));
        user.setTotalScore(rs.getInt(6));
        user.setImageUrl(rs.getString(7));
        user.setAdmin(rs.getBoolean(8));
        List<Activity> activities = new ArrayList<Activity>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM ACTIVITY WHERE USERID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, rs.getInt(1));
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                activities.add(extractActivity(resultSet));
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }

        user.setActivities(activities);
        return user;
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
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmailAddress());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
        return false;
    }

    /**
     * Get's the last 7 activities from all the users someone is following.
     *
     * @param userIds The ID's to check from.
     * @return All of the activities sorted by date.
     * @throws SQLException Exception if SQL fails.
     */
     public ArrayList<Activity> getActivitiesByUsers(ArrayList<Integer> userIds) throws SQLException
    {
        StringBuilder userIdString = new StringBuilder();

        for (Integer userId : userIds) {
            userIdString.append(userId).append(", ");
        }

        // Trim the last ", " from the string to make the syntax work.
        userIdString = new StringBuilder(userIdString.substring(0, userIdString.length() - 2));

        String sql = String.format("SELECT * FROM ACTIVITY WHERE USERID IN (%s) ORDER BY DATE LIMIT 7", userIdString);

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            ArrayList<Activity> activities = new ArrayList<>();

            while (resultSet.next()) {
                activities.add(extractActivity(resultSet));
            }

            return activities;
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, null);
        }
    }

    /**
     * Checks if a email is a admin.
     * @param email The user to check.
     * @return If the user exists
     * @throws SQLException Exception if SQL fails.
     */
    public boolean CheckIfMailIsAdmin(String email)throws SQLException {
        String sql = "SELECT ADMIN FROM `USER` where E_MAILADRES = ?";
        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
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
        user.setAdmin(rs.getBoolean(8));
        return user;
    }

    /**
     * Extracts an activity from a result set.
     * @param resultSet The result set.
     * @return The extracted activity.
     * @throws SQLException Exception if SQL fails.
     */
    public Activity extractActivity(ResultSet resultSet) throws SQLException {
        Activity activity = new Activity();
        activity.setActivityId(resultSet.getInt(1));
        activity.setUserId(resultSet.getInt(2));
        activity.setPoint(resultSet.getInt(3));
        activity.setDuration(resultSet.getLong(4));
        activity.setDistance(resultSet.getFloat(5));
        activity.setRouteId(resultSet.getInt(6));
        activity.setDateTime(resultSet.getDate(7).toString());

        return activity;
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
