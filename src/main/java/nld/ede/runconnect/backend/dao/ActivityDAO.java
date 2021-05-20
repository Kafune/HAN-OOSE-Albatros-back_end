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

    @Override
    public void addNewActivity(Activity activity) throws SQLException {
        String sql = "INSERT INTO activity (routeId, userId, point, duration, distance) Values (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, activity.getRouteId());
            statement.setInt(2, activity.getUserId());
            statement.setInt(3, activity.getPoint());
            statement.setLong(4, activity.getDuration());
            statement.setInt(5, activity.getDistance());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
