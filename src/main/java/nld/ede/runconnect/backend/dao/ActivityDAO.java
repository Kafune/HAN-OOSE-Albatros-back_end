package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActivityDAO implements IActivityDAO {
    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addNewActivity(Activity activity) throws SQLException {
        String sql = "INSERT INTO activity (routeId, userId, point, duration, tempo, distance) Values (?, ?, ?, ?, ?, ?)";
        int routeId = activity.getRouteId();
        int userId = activity.getUserId();
        int point = activity.getPoint();
        long duration = activity.getDuration();
        int tempo = activity.getTempo();
        int distance = activity.getDistance();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, routeId);
            statement.setInt(2, userId);
            statement.setInt(3, point);
            statement.setLong(4, duration);
            statement.setInt(5, tempo);
            statement.setInt(6, distance);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        }
    }
}
