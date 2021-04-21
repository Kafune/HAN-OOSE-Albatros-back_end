package nld.ede.runconnect.backend.service.dto;

import nld.ede.runconnect.backend.domain.Coordinates;

public class SegmentDTO {
    public class Segment {
        public int id;
        public CoordinatesDTO startCoordinates;
        public CoordinatesDTO endCoordinates;
    }
}
