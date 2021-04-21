package nld.ede.runconnect.backend.service.dto;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.domain.Segment;

import java.util.ArrayList;
import java.util.List;

public class DTOconverter {
    private static final Gson JSON = new Gson();

    public static RouteDTO JSONToRouteDTO(String JSONObject) throws JsonSyntaxException {
        RouteDTO newRoute;
        newRoute = JSON.fromJson(JSONObject, RouteDTO.class);

        return newRoute;
    }

    public static RouteDTO domainToRouteDTO(Route route) {

        // Maak een lijst of segmentDTO for deze route
        List<SegmentDTO> segmentDTOList = new ArrayList<>();
        domainToSegmentDTO(route);
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.routeId = route.getRouteId();
        routeDTO.distance = route.getDistance();
        routeDTO.name = route.getName();
        routeDTO.segments = segmentDTOList;
        return routeDTO;

    }

    private static void domainToSegmentDTO(Route route) {
        for (Segment item : route.getSegments()) {

            SegmentDTO segmentDTO = new SegmentDTO();
            segmentDTO.id = item.getId();

            //Start coordinate of segment
            CoordinateDTO startCoordinateDTO = new CoordinateDTO();

            startCoordinateDTO.altitude = item.getStartCoordinate().getAltitude();
            startCoordinateDTO.latitude = item.getStartCoordinate().getLatitude();
            startCoordinateDTO.longitude = item.getStartCoordinate().getLongitude();
            segmentDTO.startCoordinate = startCoordinateDTO;

            //End coordinate of segment
            CoordinateDTO endCoordinateDTO = new CoordinateDTO();

            startCoordinateDTO.altitude = item.getEndCoordinate().getAltitude();
            startCoordinateDTO.latitude = item.getEndCoordinate().getLatitude();
            startCoordinateDTO.longitude = item.getEndCoordinate().getLongitude();
            segmentDTO.endCoordinate = endCoordinateDTO;
        }
    }

}
