package nld.ede.runconnect.backend.service.dto;

import java.util.ArrayList;
import java.util.List;

public class RouteDTO {
    public String name;
    public int routeId;
    public int distance;
    public String description;
    public List<SegmentDTO> segments = new ArrayList<>();
}
