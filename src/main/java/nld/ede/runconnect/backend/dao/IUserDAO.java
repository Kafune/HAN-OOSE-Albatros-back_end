package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserDAO
{
    ArrayList<User> searchForUsers(String searchValue) throws SQLException;

    boolean toggleFollow(boolean follow, int followerId, int followeeId) throws SQLException;

    boolean isFollowing(int followerId, int followeeId) throws SQLException;
}
