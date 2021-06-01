package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.domain.Segment;
import nld.ede.runconnect.backend.service.Routes;
import nld.ede.runconnect.backend.service.dto.CoordinateDTO;
import nld.ede.runconnect.backend.service.dto.POIDTO;
import nld.ede.runconnect.backend.service.dto.RouteDTO;
import nld.ede.runconnect.backend.service.dto.SegmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RouteDAOTest {

    private RouteDAO sut;

    @BeforeEach
    public void setSut() {
        sut = new RouteDAO();
    }

    @Test
    public void getAllRoutesTest() {
        String sql = "SELECT * FROM ROUTE";
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
            List<Route> route = sut.getAllRoutes();

            /**** Assert ****/
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).executeQuery();
            assertEquals(0, route.size());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void makeRoute() {
        // Arrange

        //mock
        DataSource dataSource = mock(DataSource.class);
        try {
            when(dataSource.getConnection()).thenThrow(new SQLException());
        } catch (SQLException throwables) {
            fail(throwables);
        }
        sut.setDataSource(dataSource);

        // Act / assert

        assertThrows(
                SQLException.class,
                () -> sut.addNewRoute(new Route())
        );
    }

    @Test
    void insertSegments() {
        // Arrange
        Route route = new Route();
        Segment segment = new Segment();
        Coordinate coordinate = new Coordinate();
        coordinate.setAltitude(1);
        coordinate.setLongitude(1);
        coordinate.setLatitude(1);
        segment.setSequenceNr(0);
        Segment segment2 = new Segment();
        segment2.setSequenceNr(0);
        ArrayList<Segment> list = new ArrayList<>();
        list.add(segment);
        list.add(segment2);
        route.setSegments(list);

        //mock
        DataSource dataSource = mock(DataSource.class);
        try {
            when(dataSource.getConnection()).thenThrow(new SQLException());
        } catch (SQLException throwables) {
            fail(throwables);
        }
        sut.setDataSource(dataSource);

        // Act / assert

        assertThrows(
                SQLException.class,
                () -> sut.insertSegments(route, "", 1)
        );
    }

    @Test
    public void extractRouteTest() {
        int routeId = 2;
        String name = "Test avv";
        int distance = 3;
        String description = "Test beschrijving test";
        try {
            ResultSet rs = mock(ResultSet.class);
            when(rs.getInt(1)).thenReturn(routeId);
            when(rs.getString(2)).thenReturn(name);
            when(rs.getInt(3)).thenReturn(distance);
            when(rs.getString(4)).thenReturn(description);

            Route actualRoute = sut.extractRoute(rs);

            assertEquals(routeId, actualRoute.getRouteId());
            assertEquals(name, actualRoute.getName());
            assertEquals(distance, actualRoute.getDistance());
        } catch (SQLException e) {
            fail(e);
        }
    }

}
