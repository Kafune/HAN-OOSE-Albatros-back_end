package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.User;

import java.sql.SQLException;

public interface IRegistrationDAO {

    void registerUser(User user) throws SQLException;
}
