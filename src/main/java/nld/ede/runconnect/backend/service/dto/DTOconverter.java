package nld.ede.runconnect.backend.service.dto;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import nld.ede.runconnect.backend.domain.*;

import java.util.ArrayList;
import java.util.List;

public class DTOconverter {
    private static final Gson JSON = new Gson();

    public static RouteDTO JSONToRouteDTO(String JSONObject) throws JsonSyntaxException {
        RouteDTO newRoute;
        newRoute = JSON.fromJson(JSONObject, RouteDTO.class);

        return newRoute;
    }

    public static ActivityDTO JSONToActivityDTO(String JSONObject) throws JsonSyntaxException {
        return JSON.fromJson(JSONObject, ActivityDTO.class);
    }

    public static RouteDTO domainToRouteDTO(Route route) {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.routeId = route.getRouteId();
        routeDTO.distance = route.getDistance();
        routeDTO.name = route.getName();
        routeDTO.description = route.getDescription();
        return routeDTO;
    }

    public static SegmentDTO domainToSegmentDTO(Segment segment) {
        SegmentDTO segmentDTO = new SegmentDTO();
        segmentDTO.id = segment.getId();
        segmentDTO.startCoordinate = domainToStartCoordinateDTO(segment);
        segmentDTO.endCoordinate = domainToEndCoordinateDTO(segment);
        segmentDTO.poi = domainTOPoiDTO(segment);


        return segmentDTO;
    }

    private static POIDTO domainTOPoiDTO(Segment segment) {
        POIDTO poiDTO = new POIDTO();
        if(segment.getPOI().getDescription() != null) {
            poiDTO.description = segment.getPOI().getDescription();
            poiDTO.name = segment.getPOI().getName();
        }
        return poiDTO;

    }

    public static CoordinateDTO domainToEndCoordinateDTO(Segment item) {
        //End coordinate of segment
        CoordinateDTO endCoordinateDTO = new CoordinateDTO();

        endCoordinateDTO.altitude = item.getEndCoordinate().getAltitude();
        endCoordinateDTO.latitude = item.getEndCoordinate().getLatitude();
        endCoordinateDTO.longitude = item.getEndCoordinate().getLongitude();

        return endCoordinateDTO;
    }

    public static CoordinateDTO domainToStartCoordinateDTO(Segment item) {
        //Start coordinate of segment
        CoordinateDTO startCoordinateDTO = new CoordinateDTO();

        startCoordinateDTO.altitude = item.getStartCoordinate().getAltitude();
        startCoordinateDTO.latitude = item.getStartCoordinate().getLatitude();
        startCoordinateDTO.longitude = item.getStartCoordinate().getLongitude();

        return startCoordinateDTO;
    }
    public static Route RouteDTOToDomainRoute(RouteDTO routeDTO){
        Route route = new Route();
        route.setName(routeDTO.name);
        route.setRouteId(routeDTO.routeId);
        route.setDistance(routeDTO.distance);
        route.setDescription(routeDTO.description);
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
        if (!(segmentDTO.poi == null)) {
            segment.setPOI(POIDTOToDomainPOI(segmentDTO.poi));
        }
        return segment;
    }

    public static Coordinate CoordinateDTOToDomainCoordinate(CoordinateDTO coordinateDTO){
        Coordinate coordinate = new Coordinate();
        coordinate.setLongitude(coordinateDTO.longitude);
        coordinate.setLatitude(coordinateDTO.latitude);
        coordinate.setAltitude(coordinateDTO.altitude);
        return coordinate;
    }

    public static POI POIDTOToDomainPOI(POIDTO poiDTO){
        POI poi = new POI();
        poi.setId(poiDTO.id);
        poi.setDescription(poiDTO.description);
        poi.setName(poiDTO.name);
        return poi;
    }

    public static UserDTO domainToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmailAddress(user.getEmailAddress());
        userDTO.setUsername(user.getUsername());
        userDTO.setTotalScore(user.getTotalScore());
        userDTO.setGoogleId(user.getGoogleId());
        userDTO.setImageUrl(user.getImageUrl());

        return userDTO;
    }

    public static Activity ActivityDTOToDomainActivity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setRouteId(activityDTO.routeId);
        activity.setUserId(activityDTO.userId);
        activity.setPoint(activityDTO.point);
        activity.setDuration(activityDTO.duration);
        return activity;
    }
}
