package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ActivityDAO implements IActivityDAO {
    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    @Override
    public List<Activity> getActivityOfRoute(int routeId) throws SQLException {
//        //todo: get activities based on the logged in user, receive user id from token or something
//        String sql = "SELECT * FROM Activities";
//        try {
//            return null;
//        } catch(SQLException exception) {
//            throw exception;
//        }
        return null;
    }

    @Override
    public void addNewActivity(Activity activity) throws SQLException {
        //todo: save activity based on logged in user, for now use an existing user?
        String sql = "INSERT INTO ACTIVITIES (ROUTEID, USERID, POINT, DURATION, TEMPO, DISTANCE) Values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
