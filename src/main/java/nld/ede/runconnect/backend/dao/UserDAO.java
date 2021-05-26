package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements IUserDAO
{
    private final String SEARCH_USERS_QUERY = "";

    /**
     * Searches for users by search value.
     * @param searchValue The value to search for.
     * @return The found users, if any.
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public ArrayList<User> searchForUsers(String searchValue) throws SQLException
    {
        return null;
    }
}
