package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;

import java.sql.SQLException;
import java.util.List;

public class ActivityDAO implements IActivityDAO {
    @Override
    public List<Activity> getAllActivitiesFromUser() throws SQLException {
        //todo: get activities based on the logged in user, receive user id from token or something
        String sql = "SELECT * FROM Activities";
        try {
            return null;
        } catch(SQLException exception) {
            throw exception;
        }
    }

    @Override
    public void saveActivity() throws SQLException {
        //todo:
        String sql = "INSERT INTO Activities";
    }
}
