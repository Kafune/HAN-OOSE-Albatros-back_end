package nld.ede.runconnect.backend.service.dto;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.domain.Segment;

import java.util.ArrayList;
import java.util.List;

public class DTOconverter {
    private static final Gson JSON = new Gson();

    public static RouteDTO JSONToRouteDTO(String JSONObject) throws JsonSyntaxException{
        RouteDTO newRoute;
        newRoute = JSON.fromJson(JSONObject, RouteDTO.class);

        return newRoute;
    }

    public static Route RouteDTOToDomainRoute(RouteDTO routeDTO){
        Route route = new Route();
        route.setName(routeDTO.name);
        route.setRouteId(routeDTO.routeId);
        route.setDistance(routeDTO.distance);
        List<Segment> segments = new ArrayList<>();
        for(SegmentDTO segmentDTO : routeDTO.segments){
            segments.add(SegmentDTOToDomainSegment(segmentDTO));
        }
        route.setSegments(segments);
        return route;
    }

    public static Segment SegmentDTOToDomainSegment(SegmentDTO segmentDTO){
        Segment segment =new Segment();
        segment.setId(segmentDTO.id);
        segment.setStartCoordinate(CoordinateDTOToDomainCoordinate(segmentDTO.startCoordinate));
        segment.setEndCoordinate(CoordinateDTOToDomainCoordinate(segmentDTO.endCoordinate));
        return segment;
    }

    public static Coordinate CoordinateDTOToDomainCoordinate(CoordinateDTO coordinateDTO){
        Coordinate coordinate = new Coordinate();
        coordinate.setLongitude(coordinateDTO.longitude);
        coordinate.setLatitude(coordinateDTO.latitude);
        coordinate.setAltitude(coordinateDTO.altitude);
        return coordinate;
    }


}
