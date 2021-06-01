package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.domain.Segment;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static nld.ede.runconnect.backend.dao.helpers.ConnectionHandler.close;

public class ActivityDAO implements IActivityDAO {
    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    /**
     * Adds a new activity to the database.
     * @param activity The activity to add.
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public void addNewActivity(Activity activity) throws SQLException {

        String sql = "INSERT INTO ACTIVITY (routeId, userId, point, duration, distance) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            if (activity.getRouteId() != -1 ) {
                statement.setInt(1, activity.getRouteId());
            }
            else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setInt(2, activity.getUserId());
            statement.setInt(3, activity.getPoint());
            statement.setLong(4, activity.getDuration());
            statement.setFloat(5, activity.getDistance());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            close(statement, null);
        }
        insertSegments(activity);
    }

    /**
     * Inserts segments in the database.
     * @param activity The activity to which to add the segments.
     * @throws SQLException Exception if SQL fails.
     */
    public void insertSegments(Activity activity) throws SQLException {
        /*
         * Insert every segment with a for loop and a custom made database procedure.
         */
        for (Segment segment: activity.getSegments()) {
            int incrementedId = activity.getSegments().indexOf(segment) + 1;

            String sql = "CALL spr_InsertActivitySegments(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = dataSource.getConnection()) {
                statement = connection.prepareStatement(sql);
                statement.setInt(1, activity.getUserId());
                statement.setInt(2, activity.getPoint());
                statement.setLong(3, activity.getDuration());
                statement.setFloat(4, activity.getDistance());
                statement.setInt(5, incrementedId);
                statement.setDouble(6, segment.getStartCoordinate().getLatitude());
                statement.setDouble(7, segment.getStartCoordinate().getLongitude());
                statement.setFloat(8, segment.getStartCoordinate().getAltitude());
                statement.setDouble(9, segment.getEndCoordinate().getLatitude());
                statement.setDouble(10, segment.getEndCoordinate().getLongitude());
                statement.setFloat(11, segment.getEndCoordinate().getAltitude());
                statement.executeUpdate();
            } catch (SQLException exception) {
                throw exception;
            } finally {
                close(statement, null);
            }
        }
    }

    /**
     * Selects activities based on userId from the database
     * @param userId id of a user
     * @return list of activities
     * @throws SQLException if SQL fails.
     */
    @Override
    public List<Activity> getActivities(int userId) throws SQLException {
        ArrayList<Activity> foundActivities = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String findActivitiesQuery = "SELECT * FROM ACTIVITY WHERE USERID = ?";
            statement = connection.prepareStatement(findActivitiesQuery);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foundActivities.add(extractActivity(resultSet));
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
        return foundActivities;
    }

    /**
     * extracts a activity domain object from result set
     * @param resultSet result set. Rows from the database
     * @return a single activity object
     * @throws SQLException is SQL fails.
     */
    public Activity extractActivity(ResultSet resultSet) throws SQLException {
        Activity activity = new Activity();
        activity.setActivityId(resultSet.getInt(1));
        activity.setUserId(resultSet.getInt(2));
        activity.setPoint(resultSet.getInt(3));
        activity.setDuration(resultSet.getLong(4));
        activity.setDistance(resultSet.getFloat(5));
        activity.setRouteId(resultSet.getInt(6));

        return activity;
    }

    /**
     * Sets data source.
     * @param dataSource the data source
     */
    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
