package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserDAO
{
    ArrayList<User> searchForUsers(String searchValue) throws SQLException;

    ArrayList<Activity> getActivitiesByUsers(ArrayList<Integer> userIds) throws SQLException;

    boolean registerUser(User user) throws SQLException;

    User findUser(String googleId) throws SQLException;

    boolean CheckIfMailIsAdmin(String email) throws SQLException;

    User getById(int userId) throws SQLException;

    ArrayList<Integer> getFollowingUsers(int userId) throws SQLException;

    boolean toggleFollow(boolean follow, int followerId, int followeeId) throws SQLException;

    boolean isFollowing(int followerId, int followeeId) throws SQLException;
}
