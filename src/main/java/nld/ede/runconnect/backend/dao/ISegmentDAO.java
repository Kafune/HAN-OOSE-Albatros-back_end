package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Segment;

import java.util.List;

public interface ISegmentDAO {
    List<Segment> getSegmentsOfRoute(int id);
}
