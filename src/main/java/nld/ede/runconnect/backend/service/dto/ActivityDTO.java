package nld.ede.runconnect.backend.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ActivityDTO {
    public int routeId;
    public int userId;
    public int point;
    public long duration;
    public float distance;
    public List<SegmentDTO> segments = new ArrayList<>();
    public String date;

    public int getRouteId()
    {
        return routeId;
    }

    public void setRouteId(int routeId)
    {
        this.routeId = routeId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getPoint()
    {
        return point;
    }

    public void setPoint(int point)
    {
        this.point = point;
    }

    public long getDuration()
    {
        return duration;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
    }

    public float getDistance()
    {
        return distance;
    }

    public void setDistance(float distance)
    {
        this.distance = distance;
    }

    public List<SegmentDTO> getSegments()
    {
        return segments;
    }

    public void setSegments(List<SegmentDTO> segments)
    {
        this.segments = segments;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
