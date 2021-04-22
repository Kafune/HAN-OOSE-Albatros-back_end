package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.domain.Segment;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO implements IRouteDAO {

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    @Override
    public List<Route> getAllRoutes() {
        String sql = "SELECT * FROM ROUTE";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Route> routeList = new ArrayList<>();
            while (resultSet.next()) {
                routeList.add(extractRoute(resultSet));
            }
            return routeList;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;

    }

    private Route extractRoute(ResultSet resultSet) throws SQLException {
        Route route = new Route();
        route.setRouteId(resultSet.getInt(1));
        route.setName(resultSet.getString(2));
        route.setDistance(resultSet.getInt(3));
        return route;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
