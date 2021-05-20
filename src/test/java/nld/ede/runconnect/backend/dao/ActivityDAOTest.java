package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ActivityDAOTest {
    DataSource dataSource;
    Connection connection;
    PreparedStatement preparedStatement;
    ActivityDAO sut;

    @BeforeEach
    public void setSut() {
        sut = new ActivityDAO();
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        sut.setDataSource(dataSource);
    }

    @Test
    public void addNewActivityTest() {
        String sql = "INSERT INTO activity (routeId, userId, point, duration, distance) Values (?, ?, ?, ?, ?)";
        Activity activity = new Activity();

        try {
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            sut.setDataSource(dataSource);

            //act
            sut.addNewActivity(activity);

            //assert
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, activity.getRouteId());
            verify(preparedStatement).setInt(2, activity.getUserId());
            verify(preparedStatement).setInt(3, activity.getPoint());
            verify(preparedStatement).setLong(4, activity.getDuration());
            verify(preparedStatement).setInt(5, activity.getDistance());
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    public void insertSegmentsTest() {
        String sql = "CALL spr_InsertActivitySegments(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Activity activity = getActivity();

        try {
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            sut.setDataSource(dataSource);

            //act
            sut.insertSegments(activity);

            //assert
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, activity.getUserId());
            verify(preparedStatement).setInt(2, activity.getPoint());
            verify(preparedStatement).setLong(3, activity.getDuration());
            verify(preparedStatement).setInt(4, activity.getDistance());
            verify(preparedStatement).setDouble(6, activity.getSegments().get(0).getStartCoordinate().getLatitude());
            verify(preparedStatement).setDouble(7, activity.getSegments().get(0).getStartCoordinate().getLongitude());
            verify(preparedStatement).setFloat(8, activity.getSegments().get(0).getStartCoordinate().getAltitude());
            verify(preparedStatement).setDouble(9, activity.getSegments().get(0).getEndCoordinate().getLatitude());
            verify(preparedStatement).setDouble(10, activity.getSegments().get(0).getEndCoordinate().getLongitude());
            verify(preparedStatement).setFloat(11, activity.getSegments().get(0).getEndCoordinate().getAltitude());
            verify(preparedStatement).executeUpdate();


        } catch (Exception e) {
            fail(e);
        }
    }

    private Activity getActivity() {
        Activity activity = new Activity();
        activity.setSegments(getListOfSegments());
        activity.setDistance(2);
        activity.setDuration(3);
        activity.setPoint(21);
        activity.setUserId(65);
        activity.setRouteId(76);

        return activity;
    }

    private List<Segment> getListOfSegments() {
        List<Segment> segments = new ArrayList<>();
        Segment segment1 = new Segment();
        segment1.setId(1);
        segment1.setEndCoordinate(getCoordinate(1));
        segment1.setStartCoordinate(getCoordinate(2));
        segment1.setSequenceNr(1);
        segment1.setPOI(null);


        segments.add(segment1);

        return segments;

    }

    private Coordinate getCoordinate(int i) {
        Coordinate coordinate = new Coordinate();
        if (i == 0) {
            coordinate.setAltitude(124223);
            coordinate.setLatitude(32432);
            coordinate.setLongitude(332);
        }
        else {

            coordinate.setAltitude(56576676);
            coordinate.setLatitude(3244552);
            coordinate.setLongitude(352);
        }

        return coordinate;
    }

}
