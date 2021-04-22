package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.dao.RouteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoutesTest {

    private Routes sut;

    @BeforeEach
    public void setup() {
        sut = new Routes();
    }

    @Test
    public void findAllRoutesCallsMethodeInDAOTest() {

        IRouteDAO routeDAOMock = mock(RouteDAO.class);
        when(routeDAOMock.getAllRoutes()).thenReturn(null);
        sut.setRoutesDAO(routeDAOMock);

        sut.findAllRoutes();
        verify(routeDAOMock).getAllRoutes();
    }
    @Test
    public void findAllRoutesReturns404IfNotFoundInDatabase() {

        int expectedStatus = 404;
        IRouteDAO routeDAOMock = mock(RouteDAO.class);
        when(routeDAOMock.getAllRoutes()).thenReturn(null);
        sut.setRoutesDAO(routeDAOMock);

        Response response = sut.findAllRoutes();
        assertEquals(expectedStatus, response.getStatus());

    }

}
