package nld.ede.runconnect.backend.service.dto;

import java.util.ArrayList;
import java.util.List;

public class RouteDTO {

    private int routeId;
    private int distance;
    private List<SegmentDTO> segments = new ArrayList<>();

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<SegmentDTO> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentDTO> segments) {
        this.segments = segments;
    }
}