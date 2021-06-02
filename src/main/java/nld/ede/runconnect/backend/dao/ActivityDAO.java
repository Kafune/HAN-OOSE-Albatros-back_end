package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.domain.Segment;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static nld.ede.runconnect.backend.dao.helpers.ConnectionHandler.close;

public class ActivityDAO implements IActivityDAO {
    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource = null;
    private PreparedStatement statement = null;

    /**
     * Adds a new activity to the database.
     * @param activity The activity to add.
     * @throws SQLException Exception if SQL fails.
     */
    @Override
    public void addNewActivity(Activity activity) throws SQLException {
        Integer routeId = null;
        if (activity.getRouteId() != -1) {
            routeId = activity.getRouteId();
        }
        String sql = "INSERT INTO ACTIVITY (routeId, userId, point, duration, distance) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, routeId);
            statement.setInt(2, activity.getUserId());
            statement.setInt(3, activity.getPoint());
            statement.setLong(4, activity.getDuration());
            statement.setFloat(5, activity.getDistance());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, null);
        }
        insertSegments(activity);
    }

    @Override
    public ArrayList<Activity> getActivitiesByUsers(ArrayList<Integer> userIds) throws SQLException
    {
        return null;
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
     * Sets data source.
     * @param dataSource the data source
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
