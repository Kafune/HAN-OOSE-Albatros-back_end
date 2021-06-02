package nld.ede.runconnect.backend.service.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import nld.ede.runconnect.backend.domain.*;
import nld.ede.runconnect.backend.service.dto.*;

import java.util.ArrayList;
import java.util.List;

public class DTOconverter {
    private static final Gson JSON = new Gson();

    /**
     * Maps JSON to a route DTO.
     * @param JSONObject The given JSON data.
     * @return The mapped route DTO.
     * @throws JsonSyntaxException Exception if fromJson fails.
     */
    public static RouteDTO JSONToRouteDTO(String JSONObject) throws JsonSyntaxException {
        RouteDTO newRoute;
        newRoute = JSON.fromJson(JSONObject, RouteDTO.class);

        return newRoute;
    }

    /**
     * Maps JSON to an activity DTO.
     * @param JSONObject The given JSON.
     * @return The mapped activity DTO.
     * @throws JsonSyntaxException Exception if fromJson fails.
     */
    public static ActivityDTO JSONToActivityDTO(String JSONObject) throws JsonSyntaxException {
        return JSON.fromJson(JSONObject, ActivityDTO.class);
    }

    /**
     * Converts a domain to a route DTO.
     * @param route The domain.
     * @return The route DTO.
     */
    public static RouteDTO domainToRouteDTO(Route route) {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.routeId = route.getRouteId();
        routeDTO.distance = route.getDistance();
        routeDTO.name = route.getName();
        routeDTO.description = route.getDescription();
        return routeDTO;
    }

    /**
     * Converts a segment domain to a segment DTO.
     * @param segment The domain.
     * @return The DTO.
     */
    public static SegmentDTO domainToSegmentDTO(Segment segment) {
        SegmentDTO segmentDTO = new SegmentDTO();
        segmentDTO.id = segment.getId();
        segmentDTO.startCoordinate = domainToStartCoordinateDTO(segment);
        segmentDTO.endCoordinate = domainToEndCoordinateDTO(segment);
        segmentDTO.poi = domainTOPoiDTO(segment);


        return segmentDTO;
    }

    /**
     * Converts a segment domain to a segment DTO.
     * @param segment The domain.
     * @return The DTO.
     */
    private static POIDTO domainTOPoiDTO(Segment segment) {
        POIDTO poiDTO = new POIDTO();
        if (segment.getPOI().getDescription() != null) {
            poiDTO.description = segment.getPOI().getDescription();
            poiDTO.name = segment.getPOI().getName();
        }
        return poiDTO;

    }

    /**
     * Converts an item domain to a item DTO.
     * @param item The domain.
     * @return The domain.
     */
    public static CoordinateDTO domainToEndCoordinateDTO(Segment item) {
        //End coordinate of segment
        CoordinateDTO endCoordinateDTO = new CoordinateDTO();

        endCoordinateDTO.altitude = item.getEndCoordinate().getAltitude();
        endCoordinateDTO.latitude = item.getEndCoordinate().getLatitude();
        endCoordinateDTO.longitude = item.getEndCoordinate().getLongitude();

        return endCoordinateDTO;
    }

    /**
     * Converts a segment domain to a start coordinate DTO.
     * @param item The segment domain.
     * @return The start coordinate DTO.
     */
    public static CoordinateDTO domainToStartCoordinateDTO(Segment item) {
        //Start coordinate of segment
        CoordinateDTO startCoordinateDTO = new CoordinateDTO();

        startCoordinateDTO.altitude = item.getStartCoordinate().getAltitude();
        startCoordinateDTO.latitude = item.getStartCoordinate().getLatitude();
        startCoordinateDTO.longitude = item.getStartCoordinate().getLongitude();

        return startCoordinateDTO;
    }

    /**
     * Converts a route DTO to a route domain.
     * @param routeDTO The DTO.
     * @return The domain.
     */
    public static Route RouteDTOToDomainRoute(RouteDTO routeDTO) {
        Route route = new Route();
        route.setName(routeDTO.name);
        route.setRouteId(routeDTO.routeId);
        route.setDistance(routeDTO.distance);
        route.setDescription(routeDTO.description);
        List<Segment> segments = new ArrayList<>();
        for (SegmentDTO segmentDTO : routeDTO.segments) {
            segments.add(SegmentDTOToDomainSegment(segmentDTO));
        }
        route.setSegments(segments);
        return route;
    }

    /**
     * Converts a segment DTO to a segment domain.
     * @param segmentDTO The DTO.
     * @return The domain.
     */
    public static Segment SegmentDTOToDomainSegment(SegmentDTO segmentDTO) {
        Segment segment = new Segment();
        segment.setId(segmentDTO.id);
        segment.setStartCoordinate(CoordinateDTOToDomainCoordinate(segmentDTO.startCoordinate));
        segment.setEndCoordinate(CoordinateDTOToDomainCoordinate(segmentDTO.endCoordinate));
        if (segmentDTO.poi != null) {
            segment.setPOI(POIDTOToDomainPOI(segmentDTO.poi));
        }
        return segment;
    }

    /**
     * Converts a coordinate DTO to a coordinate domain.
     * @param coordinateDTO The DTO.
     * @return The domain.
     */
    public static Coordinate CoordinateDTOToDomainCoordinate(CoordinateDTO coordinateDTO) {
        Coordinate coordinate = new Coordinate();
        coordinate.setLongitude(coordinateDTO.longitude);
        coordinate.setLatitude(coordinateDTO.latitude);
        coordinate.setAltitude(coordinateDTO.altitude);
        return coordinate;
    }

    /**
     * Converts a POI DTO to a POI domain.
     * @param poiDTO The DTO.
     * @return The domain.
     */
    public static POI POIDTOToDomainPOI(POIDTO poiDTO) {
        POI poi = new POI();
        poi.setId(poiDTO.id);
        poi.setDescription(poiDTO.description);
        poi.setName(poiDTO.name);
        return poi;
    }

    /**
     * Converts a list of user domains to a list of user DTOs.
     * @param users The user domains to convert.
     * @return The DTOs.
     */
    public static ArrayList<UserDTO> domainsToUserDTOs(ArrayList<User> users) {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();

        for (User user: users) {
            userDTOs.add(domainToUserDTO(user));
        }

        return userDTOs;
    }

    /**
     * Converts a user domain to a user DTO.
     * @param user The domain
     * @return The DTO.
     */
    public static UserDTO domainToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.userId = user.getUserId();
        userDTO.firstName = user.getFirstName();
        userDTO.lastName = user.getLastName();
        userDTO.emailAddress = user.getEmailAddress();
        userDTO.username = user.getUsername();
        userDTO.totalScore = user.getTotalScore();
        userDTO.imageUrl = user.getImageUrl();
        userDTO.a61646d696e = user.isAdmin();

        return userDTO;
    }

    /**
     * Maps an Activity domain to a Activity DTO.
     *
     * @param activity The activity domain to convert.
     * @return The activity DTO.
     */
    public static ActivityDTO activityDomainToDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setUserId(activity.getUserId());
        dto.setPoint(activity.getPoint());
        dto.setDistance(activity.getDistance());
        dto.setDuration(activity.getDuration());
        dto.setRouteId(activity.getRouteId());

        return dto;
    }

    /**
     * Maps a list of Activity domains to a list of Activity DTOs.
     *
     * @param activities The activities to convert.
     * @return The list of activity domains.
     */
    public static ArrayList<ActivityDTO> activityDomainsToDTO(ArrayList<Activity> activities) {
        ArrayList<ActivityDTO> dtos = new ArrayList<>();

        for (Activity activity : activities) {
            dtos.add(activityDomainToDTO(activity));
        }

        return dtos;
    }

    /**
     * Converts an activity DTO to an activity domain.
     * @param activityDTO The DTO.
     * @return The domain.
     */
    public static Activity ActivityDTOToDomainActivity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setRouteId(activityDTO.routeId);
        activity.setUserId(1);
        activity.setPoint(activityDTO.point);
        activity.setDuration(activityDTO.duration);
        activity.setDistance(activityDTO.distance);
        for (SegmentDTO segmentDTO: activityDTO.segments) {
            activity.getSegments().add(SegmentDTOToDomainSegment(segmentDTO));
        }
        return activity;
    }
}
