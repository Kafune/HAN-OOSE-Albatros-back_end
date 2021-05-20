package nld.ede.runconnect.backend.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ActivityDTO {
    public int activityId;
    public int routeId;
    public int userId;
    public int point;
    public long duration;
    public int distance;
    public List<SegmentDTO> segments = new ArrayList<>();
}
