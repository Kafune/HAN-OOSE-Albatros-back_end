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

public class SegmentDAO implements ISegmentDAO {

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    @Override
    public List<Segment> getSegmentsOfRoute(int id) {
        String sql = getSelectStatement();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            List<Segment> segmentList = new ArrayList<>();

            while (resultSet.next()) {
                segmentList.add(extractSegment(resultSet));
            }
            return segmentList;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private Segment extractSegment(ResultSet resultSet) throws SQLException {
        Segment segment = new Segment();
        segment.setId(resultSet.getInt(1));
        segment.setSequenceNr(resultSet.getInt(2));
        segment.setStartCoordinate(extractStartCoordinate(resultSet));
        segment.setEndCoordinate(extractEndCoordinate(resultSet));
        return segment;
    }

    private Coordinate extractEndCoordinate(ResultSet resultSet) throws SQLException {
        Coordinate coordinate = new Coordinate();
        coordinate.setAltitude(resultSet.getInt(6));
        coordinate.setLongitude(resultSet.getDouble(7));
        coordinate.setLatitude(resultSet.getDouble(8));
        return coordinate;
    }

    private Coordinate extractStartCoordinate(ResultSet resultSet) throws SQLException {
        Coordinate coordinate = new Coordinate();
        coordinate.setAltitude(resultSet.getInt(3));
        coordinate.setLongitude(resultSet.getDouble(4));
        coordinate.setLatitude(resultSet.getDouble(5));
        return coordinate;
    }


    private String getSelectStatement() {
        return "SELECT s.SEGMENTID, " +
                "s.SEQUENCENR, c2.ALTITUDE AS STARTALTITUDE, c2.LONGITUDE AS STARTLONGITUDE, " +
                "c2.LATITUDE AS STARTLATITUDE, c.ALTITUDE AS ENDALTITUDE, c.LONGITUDE AS ENDLONGITUDE, " +
                "c.LATITUDE AS ENDLATITUDE, p.DESCRIPTION, p.NAME " +
                "FROM segmentinroute s INNER JOIN segment s2 on s.SEGMENTID = s2.SEGMENTID\n" +
                "INNER JOIN coordinates c on s2.ENDCOORD = c.COORDINATESID " +
                "INNER JOIN coordinates c2 on s2.STARTCOORD = c2.COORDINATESID " +
                "LEFT JOIN poi p on s2.SEGMENTID = p.SEGMENTID " +
                "WHERE s.ROUTEID = ?;";
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
