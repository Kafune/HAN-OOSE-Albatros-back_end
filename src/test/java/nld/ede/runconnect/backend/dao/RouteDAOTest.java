package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.Routes;
import nld.ede.runconnect.backend.service.dto.CoordinateDTO;
import nld.ede.runconnect.backend.service.dto.POIDTO;
import nld.ede.runconnect.backend.service.dto.RouteDTO;
import nld.ede.runconnect.backend.service.dto.SegmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteDAOTest {
    private RouteDAO routeDAO;

    @BeforeEach
    public void setUp() {
        routeDAO = new RouteDAO();
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
        routeDAO.setDataSource(dataSource);

        // Act / assert

        assertThrows(
                SQLException.class,
                () -> routeDAO.addNewRoute(new Route())
        );
    }
}