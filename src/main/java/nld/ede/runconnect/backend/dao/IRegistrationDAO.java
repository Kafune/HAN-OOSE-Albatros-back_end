package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import java.sql.SQLException;

public interface IRegistrationDAO {

    boolean registerUser(User user) throws SQLException;

    User findUser(String googleId) throws SQLException;

}
