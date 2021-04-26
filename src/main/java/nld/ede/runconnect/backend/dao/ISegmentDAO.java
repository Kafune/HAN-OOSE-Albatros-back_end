package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Segment;

import java.sql.SQLException;
import java.util.List;

public interface ISegmentDAO {
    List<Segment> getSegmentsOfRoute(int id) throws SQLException;
}
