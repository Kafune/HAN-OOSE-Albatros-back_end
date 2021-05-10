package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;

import java.sql.SQLException;
import java.util.List;

public interface IActivityDAO {
    List<Activity> getAllActivitiesFromUser(int userId) throws SQLException;
    void addNewActivity(Activity activity) throws SQLException;
}
