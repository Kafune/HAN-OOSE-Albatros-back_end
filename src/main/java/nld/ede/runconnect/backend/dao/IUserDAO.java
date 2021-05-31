package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserDAO
{
    /**
     * Searches for users by search value.
     * @param searchValue The value to search for.
     * @return The found users, if any.
     * @throws SQLException Exception if SQL fails.
     */
    ArrayList<User> searchForUsers(String searchValue) throws SQLException;

    /**
     * Follow or unfollow a user based on follower and followee ID.
     * @param follow Boolean value, if true follow, if false unfollow.
     * @param followerId The ID of the person trying to follow a user.
     * @param followeeId The ID of the user to follow.
     * @return If the method was executed successful.
     */
    boolean toggleFollow(boolean follow, int followerId, int followeeId) throws SQLException;

    /**
     * Checks if user is already following other user.
     * @param followerId The ID of the follower.
     * @param followeeId The ID of the followee.
     * @return If the follower is already following the followee.
     * @throws SQLException Exception if SQL fails.
     */
    boolean isFollowing(int followerId, int followeeId) throws SQLException;
}
