package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.domain.Segment;

import java.awt.*;
import java.sql.*;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.sql.DataSource;

public class RouteDAO implements IRouteDAO {

    @Resource(name = "jdbc/Run_Connect")
    DataSource dataSource;

    @Override
    public List<Route> findAllRoutes() {
        return null;
    }

    @Override
    public void addNewRoute(Route route) {

        /*
         * insert a route:
         */
        String sql = "INSERT INTO route (NAME, DISTANCE) Values (?, ?)";

        String name = route.getName();
        int distance = route.getDistance();


        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, distance);
            int affectedRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        for(int incrementedid =0; incrementedid< route.getSegments().size(); incrementedid++) {
            Segment segment = route.getSegments().get(incrementedid);

            String sql2 = "CALL spr_InsertSegements(?,?,?,?,?,?,?,?,?,?,?)";


            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql2);
                statement.setString(1, name);
                statement.setInt(2, distance);
                statement.setInt(3, incrementedid);
                statement.setDouble(4, segment.getStartCoordinate().getLatitude());
                statement.setDouble(5, segment.getStartCoordinate().getLongitude());
                statement.setFloat(6, segment.getStartCoordinate().getAltitude());
                statement.setDouble(7, segment.getEndCoordinate().getLatitude());
                statement.setDouble(8, segment.getEndCoordinate().getLongitude());
                statement.setFloat(9, segment.getEndCoordinate().getAltitude());
                statement.setString(10, ((segment.getPoi() == null) ? "-1" : segment.getPoi().getName()));
                statement.setString(11, ((segment.getPoi() == null) ? "-1" : segment.getPoi().getDescription()));

                int affectedRows = statement.executeUpdate();

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}