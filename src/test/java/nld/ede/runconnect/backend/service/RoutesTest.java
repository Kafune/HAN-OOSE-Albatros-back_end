package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.dao.RouteDAO;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.dto.RouteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoutesTest {

    private Routes routes;
    public static final int ID = 1;
    public static final int DISTANCE = 30;
    public static final String NAME = "nameTest";

    @BeforeEach
    public void setup() {
        routes = new Routes();
    }

    @Test
    public void findAllRoutesCallsMethodeInDAOTest() {
        int expectedStatus = 404;
        IRouteDAO routeDAOMock = mock(RouteDAO.class);
        when(routeDAOMock.getAllRoutes()).thenReturn(null);
        routes.setRoutesDAO(routeDAOMock);

        Response response = routes.findAllRoutes();
        assertEquals(expectedStatus, response.getStatus());
    }
    @Test
    public void findAllRoutesReturnsCorrectObject() {
        List<Route> list = getRouteList();

        IRouteDAO routeDAOMock = mock(RouteDAO.class);
        when(routeDAOMock.getAllRoutes()).thenReturn(list);
        routes.setRoutesDAO(routeDAOMock);

        Response response = routes.findAllRoutes();
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

    @Test
    void makeRoute() {
        // Arrange
        int statusCodeExpected = 201;
        String JSON = "{\n" +
                "  \"name\": \"BosWandeling\",\n" +
                "  \"routeID\": 1,\n" +
                "  \"distance\": 5,\n" +
                "  \"segments\": [\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"startCoordinate\": {\n" +
                "        \"longitude\": 12,\n" +
                "        \"latitude\": 45,\n" +
                "        \"altitude\": 0\n" +
                "      },\n" +
                "      \"endCoordinate\": {\n" +
                "        \"longitude\": 13,\n" +
                "        \"latitude\": 45.1,\n" +
                "        \"altitude\": -2\n" +
                "      },\n" +
                "      \"poi\": {\n" +
                "        \"id\": 5,\n" +
                "        \"name\": \"\",\n" +
                "        \"description\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"startCoordinate\": {\n" +
                "        \"longitude\": 13,\n" +
                "        \"latitude\": 45.1,\n" +
                "        \"altitude\": -2\n" +
                "      },\n" +
                "      \"endCoordinate\": {\n" +
                "        \"longitude\": 14,\n" +
                "        \"latitude\": 44,\n" +
                "        \"altitude\": 3\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        //mock
        var route = new Route();
        RouteDAO RouteDAOMock = mock(RouteDAO.class);
        try {
            doNothing().when(RouteDAOMock).addNewRoute(route);
        } catch (SQLException throwables) {
            fail(throwables);
        }
        routes.setRoutesDAO(RouteDAOMock);

        // Act
        Response response = null;
        try {
            response = routes.makeRoute(JSON);
        }catch(Exception throwables){
            fail(throwables);
        }
        // Assert
        assertEquals(statusCodeExpected, response.getStatus());

        //test content

        //no contents

    }

}
