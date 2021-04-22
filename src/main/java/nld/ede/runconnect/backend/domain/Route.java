package nld.ede.runconnect.backend.domain;


import java.util.ArrayList;
import java.util.List;
import nld.ede.runconnect.backend.domain.Segment;


public class Route {
    private String name;
    private int routeId;
    private int distance;
    private List<Segment> segments = new ArrayList<>();


    public String getName() {
        return name;
    }

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

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public void setName(String name) {
        this.name = name;
    }
}
