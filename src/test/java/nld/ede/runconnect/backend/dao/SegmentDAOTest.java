package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class SegmentDAOTest {

    private SegmentDAO sut;

    @BeforeEach
    public void setSut() {
        sut = new SegmentDAO();
    }

    @Test
    public void getSegmentsOfRouteTest() {
        int id = 1;
        String sql = getSelectStatement();
        try {
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // setup classes
            sut.setDataSource(dataSource);

            /**** Act ****/
            List<Segment> segment = sut.getSegmentsOfRoute(id);

            /**** Assert ****/
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, id);
            verify(preparedStatement).executeQuery();

            assertEquals(0, segment.size());

        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    public void  extractSegmentTest() {
        int id = 3;
        int sequenceNr = 4;
        int startAlt = 5;
        double startLong = 7;
        double startLat = 8;
        int endAlt = 6;
        double endLong = 23;
        double endLat = 85;
        String name = "test";
        String description = "sabsdjhdsjhsd";

        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt(1)).thenReturn(id);
            when(rs.getInt(2)).thenReturn(sequenceNr);
            when(rs.getInt(3)).thenReturn(startAlt);
            when(rs.getDouble(4)).thenReturn(startLong);
            when(rs.getDouble(5)).thenReturn(startLat);
            when(rs.getInt(6)).thenReturn(endAlt);
            when(rs.getDouble(7)).thenReturn(endLong);
            when(rs.getDouble(8)).thenReturn(endLat);
            when(rs.getString(10)).thenReturn(name);
            when(rs.getString(9)).thenReturn(description);

            Segment actualSegment = sut.extractSegment(rs);

            assertEquals(id, actualSegment.getId());
            assertEquals(sequenceNr, actualSegment.getSequenceNr());

            assertEquals(startAlt, actualSegment.getStartCoordinate().getAltitude());
            assertEquals(startLong, actualSegment.getStartCoordinate().getLongitude());
            assertEquals(startLat, actualSegment.getStartCoordinate().getLatitude());

            assertEquals(endAlt, actualSegment.getEndCoordinate().getAltitude());
            assertEquals(endLong, actualSegment.getEndCoordinate().getLongitude());
            assertEquals(endLat, actualSegment.getEndCoordinate().getLatitude());

            assertEquals(name, actualSegment.getPOI().getName());
            assertEquals(description, actualSegment.getPOI().getDescription());

        } catch (SQLException s) {
            fail(s);
        }
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

}
