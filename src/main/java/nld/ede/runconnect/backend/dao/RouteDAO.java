package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Route;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteDAO implements IRouteDAO {

    @Override
    public List<Route> findAllRoutes() {
        return null;
    }

    @Override
    public void addNewRoute(Route newRoute) {
//        String sql = "INSERT INTO route (NAME, ROUTEID, DISTANCE) Values (?,(SELECT id from users where Token = ?))";
//
//        String name = playlistName;
//
//        try (Connection connection = dataSource.getConnection()) {
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, name);
//            statement.setString(2, token);
//            int affectedRows = statement.executeUpdate();
//            if (affectedRows < 1) {
//                throw new NoRowsAreEffectedException();
//            }
//        } catch (SQLException exception) {
//            throw exception;
//        }
    }
}