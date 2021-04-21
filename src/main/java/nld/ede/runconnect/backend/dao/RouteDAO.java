package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.Route;

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
        String sql = "INSERT INTO route (NAME, DISTANCE) Values (?, ?)";

        String name = route.getName();
        int distance = route.getDistance();


        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, distance);
            int affectedRows = statement.executeUpdate();
//            if (affectedRows < 1) {
//                throw new NoRowsAreEffectedException();
//            }
        } catch (SQLException exception) {
//            throw exception;
        }

        for(int incrementedid =0; incrementedid< route.getSegments().size(); incrementedid++)
        route.getSegments().forEach(segment -> {
            /*
             * segment startcoord:
             */

            String sql1 = "INSERT INTO coordinates (LOCATION, ALTITUDE) Values (?,?)";
            Coordinate startCoordinate = segment.getStartCoordinate();
            String location = "ST_PointFromText('POINT(" + startCoordinate.getLatitude() + " " + startCoordinate.getLongitude() + ")')";
            float altitude = startCoordinate.getAltitude();

            try (Connection connection = dataSource.getConnection()) {

                PreparedStatement statement = connection.prepareStatement(sql1);
                statement.setString(1, location);
                statement.setFloat(2, altitude);
                int affectedRows = statement.executeUpdate();
//                    if (affectedRows < 1) {
//                        throw new NoRowsAreEffectedException();
//                    }
            } catch (SQLException exception) {
//                    throw exception;
            }

            /*
             * segment endcoord:
             */

            String sql2 = "INSERT INTO coordinates (LOCATION, ALTITUDE) Values (?,?)";
            Coordinate endCoordinate = segment.getEndCoordinate();
            String location2 = "ST_PointFromText('POINT(" + endCoordinate.getLatitude() + " " + endCoordinate.getLongitude() + ")')";
            float altitude2 = endCoordinate.getAltitude();

            try (Connection connection = dataSource.getConnection()) {

                PreparedStatement statement = connection.prepareStatement(sql2);
                statement.setString(1, location2);
                statement.setFloat(2, altitude2);
                int affectedRows = statement.executeUpdate();
//                    if (affectedRows < 1) {
//                        throw new NoRowsAreEffectedException();
//                    }
            } catch (SQLException exception) {
//                    throw exception;
            }

            /*
             * segment:
             */

            String sql3 = "INSERT INTO segment (STARTCOORD, ENDCOORD) Values ((SELECT COORDINATESID from coordinates " +
                    "WHERE LOCATION = ? & ALTITUDE = ?),(SELECT COORDINATESID from coordinates where LOCATION = ? & ALTITUDE = ?))";

            try (Connection connection = dataSource.getConnection()) {

                PreparedStatement statement = connection.prepareStatement(sql3);
                statement.setString(1, location);
                statement.setFloat(2, altitude);
                statement.setString(3, location2);
                statement.setFloat(4, altitude2);
                int affectedRows = statement.executeUpdate();
//                    if (affectedRows < 1) {
//                        throw new NoRowsAreEffectedException();
//                    }
            } catch (SQLException exception) {
//                    throw exception;
            }

            /*
             * segment in route:
             */


            String sql4 = "INSERT INTO segmentinroute (SEGMENTID, SEQUENCENR) Values ((SELECT SEGMENTID from segment " +
                    "WHERE STARTCOORD = (SELECT COORDINATESID from coordinates WHERE LOCATION = ? & ALTITUDE = ?) & ENDCOORD = (SELECT COORDINATESID from coordinates WHERE LOCATION = ? & ALTITUDE = ?)),?)";

            try (Connection connection = dataSource.getConnection()) {

                PreparedStatement statement = connection.prepareStatement(sql4);
                statement.setString(1, location);
                statement.setFloat(2, altitude);
                statement.setString(3, location2);
                statement.setFloat(4, altitude2);
                statement.setInt(5,incrementedid );
                int affectedRows = statement.executeUpdate();
//                    if (affectedRows < 1) {
//                        throw new NoRowsAreEffectedException();
//                    }
            } catch (SQLException exception) {
//                    throw exception;
            }


        });


        sql = "INSERT INTO route (NAME, ROUTEID, DISTANCE) Values (?,(SELECT id from users where Token = ?))";

        String name = playlistName;

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, token);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new NoRowsAreEffectedException();
            }
        } catch (SQLException exception) {
            throw exception;
        }

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}