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
}
