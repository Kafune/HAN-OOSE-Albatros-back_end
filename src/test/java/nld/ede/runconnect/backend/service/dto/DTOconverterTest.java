package nld.ede.runconnect.backend.service.dto;

import nld.ede.runconnect.backend.dao.RouteDAO;
import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.POI;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.Routes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class DTOconverterTest {
    private DTOconverter dtoconverter;

    @BeforeEach
    public void setUp() {
        dtoconverter = new DTOconverter();
    }

    @Test
    void JSONToRouteDTO() {
        // Arrange
        CoordinateDTO startCoordinate1 = new CoordinateDTO();
        startCoordinate1.longitude = 12;
        startCoordinate1.latitude = 45;
        startCoordinate1.altitude = 0;

        CoordinateDTO endCoordinate1 = new CoordinateDTO();
        endCoordinate1.longitude = 13;
        endCoordinate1.latitude = 45.1;
        endCoordinate1.altitude = -2;

        POIDTO poi = new POIDTO();
        poi.id = 5;
        poi.description = "test";
        poi.name = "nameTest";

        SegmentDTO segmentDTO1 = new SegmentDTO();
        segmentDTO1.id = 5;
        segmentDTO1.startCoordinate = startCoordinate1;
        segmentDTO1.endCoordinate = endCoordinate1;
        segmentDTO1.poi = poi;

        CoordinateDTO startCoordinate2 = new CoordinateDTO();
        startCoordinate2.longitude = 12;
        startCoordinate2.latitude = 45;
        startCoordinate2.altitude = 0;

        CoordinateDTO endCoordinate2 = new CoordinateDTO();
        endCoordinate2.longitude = 13;
        endCoordinate2.latitude = 45.1;
        endCoordinate2.altitude = -2;


        SegmentDTO segmentDTO2 = new SegmentDTO();
        segmentDTO2.id = 6;
        segmentDTO2.startCoordinate = startCoordinate2;
        segmentDTO2.endCoordinate = endCoordinate2;

        RouteDTO routeDTO = new RouteDTO();
        routeDTO.name = "BosWandeling";
        routeDTO.routeId = 1;
        routeDTO.distance = 5;
        routeDTO.segments.add(segmentDTO1);
        routeDTO.segments.add(segmentDTO2);


        String JSON = "{ " +
                "  \"name\": \"" + routeDTO.name + "\", " +
                "  \"routeId\": " + routeDTO.routeId + ", " +
                "  \"distance\": " + routeDTO.distance + ", " +
                "  \"segments\": [ " +
                "    { " +
                "      \"id\": " + segmentDTO1.id + ", " +
                "      \"startCoordinate\": { " +
                "        \"longitude\": " + startCoordinate1.longitude + ", " +
                "        \"latitude\": " + startCoordinate1.latitude + ", " +
                "        \"altitude\": " + startCoordinate1.altitude + " " +
                "      }, " +
                "      \"endCoordinate\": { " +
                "        \"longitude\": " + endCoordinate1.longitude + ", " +
                "        \"latitude\": " + endCoordinate1.latitude + ", " +
                "        \"altitude\": " + endCoordinate1.altitude + " " +
                "      }, " +
                "      \"poi\": { " +
                "        \"id\": " + poi.id + ", " +
                "        \"name\": \"" + poi.name + "\", " +
                "        \"description\": \"" + poi.description + "\" " +
                "      } " +
                "    }, " +
                "    { " +
                "      \"id\": " + segmentDTO2.id + ", " +
                "      \"startCoordinate\": { " +
                "        \"longitude\": " + startCoordinate2.longitude + ", " +
                "        \"latitude\": " + startCoordinate2.latitude + ", " +
                "        \"altitude\": " + startCoordinate2.altitude + " " +
                "      }, " +
                "      \"endCoordinate\": { " +
                "        \"longitude\": " + endCoordinate2.longitude + ", " +
                "        \"latitude\": " + endCoordinate2.latitude + ", " +
                "        \"altitude\": " + endCoordinate2.altitude + " " +
                "      } " +
                "    } " +
                "  ] " +
                "}";


        //mock
        //nothing

        // Act
        RouteDTO response = DTOconverter.JSONToRouteDTO(JSON);

        // Assert

        //test content

        assertEquals(startCoordinate1.longitude, response.segments.get(0).startCoordinate.longitude);
        assertEquals(startCoordinate1.latitude, response.segments.get(0).startCoordinate.latitude);
        assertEquals(startCoordinate1.altitude, response.segments.get(0).startCoordinate.altitude);
        assertEquals(endCoordinate1.longitude, response.segments.get(0).endCoordinate.longitude);
        assertEquals(endCoordinate1.latitude, response.segments.get(0).endCoordinate.latitude);
        assertEquals(endCoordinate1.altitude, response.segments.get(0).endCoordinate.altitude);

        assertEquals(poi.id, response.segments.get(0).poi.id);
        assertEquals(poi.description, response.segments.get(0).poi.description);
        assertEquals(poi.name, response.segments.get(0).poi.name);

        assertEquals(segmentDTO1.id, response.segments.get(0).id);

        assertEquals(startCoordinate2.longitude, response.segments.get(1).startCoordinate.longitude);
        assertEquals(startCoordinate2.latitude, response.segments.get(1).startCoordinate.latitude);
        assertEquals(startCoordinate2.altitude, response.segments.get(1).startCoordinate.altitude);

        assertEquals(endCoordinate2.longitude, response.segments.get(1).endCoordinate.longitude);
        assertEquals(endCoordinate2.latitude, response.segments.get(1).endCoordinate.latitude);
        assertEquals(endCoordinate2.altitude, response.segments.get(1).endCoordinate.altitude);

        assertEquals(segmentDTO2.id, response.segments.get(1).id);


        assertEquals(routeDTO.name, response.name);
        assertEquals(routeDTO.routeId, response.routeId);
        assertEquals(routeDTO.distance, response.distance);

    }

    @Test
    public void RouteDTOToDomainRoute(){
        // Arrange
        CoordinateDTO startCoordinate1 = new CoordinateDTO();
        startCoordinate1.longitude = 12;
        startCoordinate1.latitude = 45;
        startCoordinate1.altitude = 0;

        CoordinateDTO endCoordinate1 = new CoordinateDTO();
        endCoordinate1.longitude = 13;
        endCoordinate1.latitude = 45.1;
        endCoordinate1.altitude = -2;

        POIDTO poi = new POIDTO();
        poi.id = 5;
        poi.description = "test";
        poi.name = "nameTest";

        SegmentDTO segmentDTO1 = new SegmentDTO();
        segmentDTO1.id = 5;
        segmentDTO1.startCoordinate = startCoordinate1;
        segmentDTO1.endCoordinate = endCoordinate1;
        segmentDTO1.poi = poi;

        CoordinateDTO startCoordinate2 = new CoordinateDTO();
        startCoordinate2.longitude = 12;
        startCoordinate2.latitude = 45;
        startCoordinate2.altitude = 0;

        CoordinateDTO endCoordinate2 = new CoordinateDTO();
        endCoordinate2.longitude = 13;
        endCoordinate2.latitude = 45.1;
        endCoordinate2.altitude = -2;


        SegmentDTO segmentDTO2 = new SegmentDTO();
        segmentDTO2.id = 6;
        segmentDTO2.startCoordinate = startCoordinate2;
        segmentDTO2.endCoordinate = endCoordinate2;

        RouteDTO routeDTO = new RouteDTO();
        routeDTO.name = "BosWandeling";
        routeDTO.routeId = 1;
        routeDTO.distance = 5;
        routeDTO.segments.add(segmentDTO1);
        routeDTO.segments.add(segmentDTO2);

        //mock
        //nothing

        // Act
        Route response = DTOconverter.RouteDTOToDomainRoute(routeDTO);

        // Assert

        //test content

        assertEquals(startCoordinate1.longitude, response.getSegments().get(0).getStartCoordinate().getLongitude());
        assertEquals(startCoordinate1.latitude, response.getSegments().get(0).getStartCoordinate().getLatitude());
        assertEquals(startCoordinate1.altitude, response.getSegments().get(0).getStartCoordinate().getAltitude());
        assertEquals(endCoordinate1.longitude, response.getSegments().get(0).getEndCoordinate().getLongitude());
        assertEquals(endCoordinate1.latitude, response.getSegments().get(0).getEndCoordinate().getLatitude());
        assertEquals(endCoordinate1.altitude, response.getSegments().get(0).getEndCoordinate().getAltitude());

        assertEquals(poi.id, response.getSegments().get(0).getPoi().getId());
        assertEquals(poi.description, response.getSegments().get(0).getPoi().getDescription());
        assertEquals(poi.name, response.getSegments().get(0).getPoi().getName());

        assertEquals(segmentDTO1.id, response.getSegments().get(0).getId());

        assertEquals(startCoordinate2.longitude, response.getSegments().get(1).getStartCoordinate().getLongitude());
        assertEquals(startCoordinate2.latitude, response.getSegments().get(1).getStartCoordinate().getLatitude());
        assertEquals(startCoordinate2.altitude, response.getSegments().get(1).getStartCoordinate().getAltitude());

        assertEquals(endCoordinate2.longitude, response.getSegments().get(1).getEndCoordinate().getLongitude());
        assertEquals(endCoordinate2.latitude, response.getSegments().get(1).getEndCoordinate().getLatitude());
        assertEquals(endCoordinate2.altitude, response.getSegments().get(1).getEndCoordinate().getAltitude());

        assertEquals(segmentDTO2.id, response.getSegments().get(1).getId());

        assertEquals(routeDTO.name, response.getName());
        assertEquals(routeDTO.routeId, response.getRouteId());
        assertEquals(routeDTO.distance, response.getDistance());

    }

}