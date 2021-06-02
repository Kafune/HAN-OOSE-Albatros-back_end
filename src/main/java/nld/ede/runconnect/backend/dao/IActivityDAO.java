package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IActivityDAO {
    void addNewActivity(Activity activity) throws SQLException;

    ArrayList<Activity> getActivitiesByUsers(ArrayList<Integer> userIds) throws SQLException;
}
