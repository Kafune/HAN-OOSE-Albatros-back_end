package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.dao.RouteDAO;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.dto.RouteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoutesTest {

    private Routes sut;
    public static final int ID = 1;
    public static final int DISTANCE = 30;
    public static final String NAME = "nameTest";

    @BeforeEach
    public void setup() {
        sut = new Routes();
    }

    @Test
    public void findAllRoutesCallsMethodeInDAOTest() {
        int expectedStatus = 404;
        IRouteDAO routeDAOMock = mock(RouteDAO.class);
        when(routeDAOMock.getAllRoutes()).thenReturn(null);
        sut.setRoutesDAO(routeDAOMock);

        Response response = sut.findAllRoutes();
        assertEquals(expectedStatus, response.getStatus());
    }
    @Test
    public void findAllRoutesReturnsCorrectObject() {
        List<Route> list = getRouteList();

        IRouteDAO routeDAOMock = mock(RouteDAO.class);
        when(routeDAOMock.getAllRoutes()).thenReturn(list);
        sut.setRoutesDAO(routeDAOMock);

        Response response = sut.findAllRoutes();
        RouteDTO expectedRouteDTO = getRouteDTO();
        List<RouteDTO> actualRouteDTO = (List<RouteDTO>) response.getEntity();

        assertEquals(expectedRouteDTO.routeId, actualRouteDTO.get(0).routeId);
        assertEquals(expectedRouteDTO.distance, actualRouteDTO.get(0).distance);
        assertEquals(expectedRouteDTO.name, actualRouteDTO.get(0).name);
        assertEquals(0, actualRouteDTO.get(0).segments.size());
    }

    private RouteDTO getRouteDTO() {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.routeId = ID;
        routeDTO.name = NAME;
        routeDTO.distance = DISTANCE;
        return routeDTO;
    }

    private List<Route> getRouteList() {
        Route route = new Route();
        route.setRouteId(ID);
        route.setDistance(DISTANCE);
        route.setName(NAME);
        List<Route> list = new ArrayList<>();
        list.add(route);
        return list;
    }

}
