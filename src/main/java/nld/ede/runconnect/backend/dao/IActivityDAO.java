package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;

import java.sql.SQLException;

public interface IActivityDAO {
    void addNewActivity(Activity activity) throws SQLException;
}
