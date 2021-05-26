package nld.ede.runconnect.backend.dao;

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

import static nld.ede.runconnect.backend.dao.helpers.ConnectionHandler.close;

public class RouteDAO implements IRouteDAO {

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    private PreparedStatement statement;
    private ResultSet resultSet;

    @Override
    public List<Route> getAllRoutes() throws SQLException {
        String sql = "SELECT * FROM ROUTE";
        try (Connection connection = dataSource.getConnection()) {

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            List<Route> routeList = new ArrayList<>();
            while (resultSet.next()) {
                routeList.add(extractRoute(resultSet));
            }
            return routeList;

        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }

    }

    @Override
    public void addNewRoute(Route route) throws SQLException {

        /*
         * insert a route:
         */
        String sql = "INSERT INTO ROUTE (NAME, DESCRIPTION,  DISTANCE) Values (?, ?, ?)";
        String name = route.getName();
        int distance = route.getDistance();
        String description = route.getDescription();

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, distance);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, null);
        }

        /*
         * Insert every segment with a for loop and a custom made database procedure.
         */
        for(int incrementedid =0; incrementedid< route.getSegments().size(); incrementedid++) {
            Segment segment = route.getSegments().get(incrementedid);

            String sql2 = "CALL spr_InsertSegements(?,?,?,?,?,?,?,?,?,?,?)";
            try (Connection connection = dataSource.getConnection()) {
                statement = connection.prepareStatement(sql2);
                statement.setString(1, name);
                statement.setInt(2, distance);
                statement.setInt(3, incrementedid);
                statement.setDouble(4, segment.getStartCoordinate().getLatitude());
                statement.setDouble(5, segment.getStartCoordinate().getLongitude());
                statement.setFloat(6, segment.getStartCoordinate().getAltitude());
                statement.setDouble(7, segment.getEndCoordinate().getLatitude());
                statement.setDouble(8, segment.getEndCoordinate().getLongitude());
                statement.setFloat(9, segment.getEndCoordinate().getAltitude());
               // -1 has been used here to indicate that this segment doesn't have a POI.
               // The database procedure checks whether it is -1 or a poi.
                statement.setString(10, ((segment.getPOI() == null) ? "-1" : segment.getPOI().getName()));
                statement.setString(11, ((segment.getPOI() == null) ? "-1" : segment.getPOI().getDescription()));
                statement.executeUpdate();
            } catch (SQLException exception) {
                throw exception;
            } finally {
                close(statement, null);
            }
        }
    }


    public Route extractRoute(ResultSet resultSet) throws SQLException {
        Route route = new Route();
        route.setRouteId(resultSet.getInt(1));
        route.setName(resultSet.getString(2));
        route.setDistance(resultSet.getInt(3));
        route.setDescription(resultSet.getString(4));
        return route;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
