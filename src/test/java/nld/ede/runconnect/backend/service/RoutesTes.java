package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.RouteDAO;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.dto.HelloworldDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class RoutesTes {
    private Routes routes;

    @BeforeEach
    public void setUp() {
        routes = new Routes();
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