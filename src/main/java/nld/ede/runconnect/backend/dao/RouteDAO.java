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

public class RouteDAO implements IRouteDAO {

    @Resource(name = "jdbc/spotitube")
    private DataSource dataSource;

    @Override
    public List<Route> findAllRoutes() {
        String sql = findAllRoutesStatement();
        try (Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Route> routeList = new ArrayList<>();
            while (resultSet.next()){
                routeList.add(extractRoute(resultSet));
            }
            return routeList;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;

    }

    private Route extractRoute(ResultSet resultSet) throws SQLException {
        int routeId = -1;
        if (routeId != resultSet.getInt(1)) {
            Route route = new Route();
            routeId = resultSet.getInt(1);
            route.setRouteId(routeId);
            route.setDistance(resultSet.getInt(2));
            route.setName(resultSet.getString(3));
            route.setSegments(extractSegment(resultSet));
        }

        return null;
    }

    private List<Segment> extractSegment(ResultSet resultSet) {
        return null;
    }

    public String findAllRoutesStatement() {
        return " SELECT r.ROUTEID, r.DISTANCE, r.NAME, s.SEGMENTID, " +
                "s.SEQUENCENR, c2.ALTITUDE AS STARTALTITUDE, c2.LONGITUDE AS STARTLONGITUDE,  " +
                "c2.LATITUDE AS STARTLATITUDE, c.ALTITUDE AS ENDALTITUDE, c.LONGITUDE AS ENDLONGITUDE,  " +
                "c.LATITUDE AS ENDLATITUDE, p.DESCRIPTION, p.NAME  " +
                "FROM segmentinroute s inner join route r on s.ROUTEID = r.ROUTEID  " +
                "INNER JOIN segment s2 on s.SEGMENTID = s2.SEGMENTID  " +
                "INNER JOIN coordinates c on s2.ENDCOORD = c.COORDINATESID  " +
                "INNER JOIN coordinates c2 on s2.STARTCOORD = c2.COORDINATESID  " +
                "LEFT JOIN poi p on s2.SEGMENTID = p.SEGMENTID;";
    }
}
