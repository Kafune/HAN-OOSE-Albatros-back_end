package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.POI;
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

public class SegmentDAO implements ISegmentDAO {

    @Resource(name = "jdbc/Run_Connect")
    private DataSource dataSource;

    private PreparedStatement statement;
    private ResultSet resultSet;

    @Override
    public List<Segment> getSegmentsOfRoute(int id) throws SQLException {
        String sql = getSelectStatement();
        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<Segment> segmentList = new ArrayList<>();

            while (resultSet.next()) {
                segmentList.add(extractSegment(resultSet));
            }
            return segmentList;

        } catch (SQLException exception) {
            throw exception;
        } finally {
            close(statement, resultSet);
        }
    }

    public Segment extractSegment(ResultSet resultSet) throws SQLException {
        Segment segment = new Segment();
        segment.setId(resultSet.getInt(1));
        segment.setSequenceNr(resultSet.getInt(2));
        segment.setStartCoordinate(extractStartCoordinate(resultSet));
        segment.setEndCoordinate(extractEndCoordinate(resultSet));
        segment.setPOI(extractPoi(resultSet));
        return segment;
    }

    private POI extractPoi(ResultSet resultSet) throws SQLException {
        POI poi = new POI();
        if (resultSet.getString(9) != null) {
            poi.setDescription(resultSet.getString(9));
        }
        if (resultSet.getString(10) != null) {
            poi.setName(resultSet.getString(10));
        }
        return poi;
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
                "FROM SEGMENTINROUTE s INNER JOIN SEGMENT s2 on s.SEGMENTID = s2.SEGMENTID\n" +
                "INNER JOIN COORDINATES c on s2.ENDCOORD = c.COORDINATESID " +
                "INNER JOIN COORDINATES c2 on s2.STARTCOORD = c2.COORDINATESID " +
                "LEFT JOIN POI p on s2.SEGMENTID = p.SEGMENTID " +
                "WHERE s.ROUTEID = ?;";
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
