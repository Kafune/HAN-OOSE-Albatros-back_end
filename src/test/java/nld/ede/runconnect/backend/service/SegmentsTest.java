package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.ISegmentDAO;
import nld.ede.runconnect.backend.dao.SegmentDAO;
import nld.ede.runconnect.backend.domain.Coordinate;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.domain.Segment;
import nld.ede.runconnect.backend.service.dto.CoordinateDTO;
import nld.ede.runconnect.backend.service.dto.RouteDTO;
import nld.ede.runconnect.backend.service.dto.SegmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SegmentsTest {

    public static final int SEGMENT_ID = 2;
    private static final int ROUTE_ID = 1;
    public static final int SEQUENCE_NR = 3;
    public static final int END_ALT = 333;
    public static final int END_LAT = 444;
    public static final int END_LON = 222;
    public static final int START_ALT = 222;
    public static final int START_LAT = 333;
    public static final int START_LON = 444;

    private Segments sut;
    private ISegmentDAO segmentDAOMock;

    @BeforeEach
    public void setSut() {
        sut = new Segments();
         segmentDAOMock = mock(SegmentDAO.class);
    }

    @Test
    public void findSegmentsOfRouteCallsMethodeInDAO() {


        when(segmentDAOMock.getSegmentsOfRoute(ROUTE_ID)).thenReturn(null);
        sut.setSegmentDAO(segmentDAOMock);

        Response response = sut.findSegmentsOfRoute(ROUTE_ID);
        verify(segmentDAOMock).getSegmentsOfRoute(ROUTE_ID);
        assertEquals(404, response.getStatus());
    }
    @Test
    public void findSegmentsOfRouteReturnsCorrectObject() {

        List<Segment> list = getSegmentList();

        when(segmentDAOMock.getSegmentsOfRoute(ROUTE_ID)).thenReturn(list);
        sut.setSegmentDAO(segmentDAOMock);

        Response response = sut.findSegmentsOfRoute(ROUTE_ID);
        SegmentDTO expectedSegmentDTO = getSegmentDTO();
        List<SegmentDTO> actualSegmentDTO = (List<SegmentDTO>) response.getEntity();

        assertEquals(expectedSegmentDTO.id, actualSegmentDTO.get(0).id);

        assertEquals(expectedSegmentDTO.endCoordinate.altitude, actualSegmentDTO.get(0).endCoordinate.altitude);
        assertEquals(expectedSegmentDTO.endCoordinate.latitude, actualSegmentDTO.get(0).endCoordinate.latitude);
        assertEquals(expectedSegmentDTO.endCoordinate.longitude, actualSegmentDTO.get(0).endCoordinate.longitude);

        assertEquals(expectedSegmentDTO.startCoordinate.longitude, actualSegmentDTO.get(0).startCoordinate.longitude);
        assertEquals(expectedSegmentDTO.startCoordinate.latitude, actualSegmentDTO.get(0).startCoordinate.latitude);
        assertEquals(expectedSegmentDTO.startCoordinate.altitude, actualSegmentDTO.get(0).startCoordinate.altitude);
    }

    private SegmentDTO getSegmentDTO() {
        SegmentDTO segmentDTO = new SegmentDTO();
        segmentDTO.id = SEGMENT_ID;
        segmentDTO.startCoordinate =  getCoordinateDTO(START_ALT, START_LAT, START_LON);
        segmentDTO.endCoordinate = getCoordinateDTO(END_ALT, END_LAT, END_LON);
        return segmentDTO;
    }

    private CoordinateDTO getCoordinateDTO(int alt, int lat, int lon) {
        CoordinateDTO coordinateDTO = new CoordinateDTO();
        coordinateDTO.longitude = lon;
        coordinateDTO.latitude = lat;
        coordinateDTO.altitude = alt;
        return coordinateDTO;
    }

    private List<Segment> getSegmentList() {
        Segment segment = new Segment();
        segment.setId(SEGMENT_ID);
        segment.setSequenceNr(SEQUENCE_NR);
        segment.setEndCoordinate(getCoordinatie(END_ALT, END_LAT, END_LON));
        segment.setStartCoordinate(getCoordinatie(START_ALT, START_LAT, START_LON));
        List<Segment> list = new ArrayList<>();
        list.add(segment);
        return list;
    }

    private Coordinate getCoordinatie(int alt, int lat, int lon) {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(lat);
        coordinate.setLongitude(lon);
        coordinate.setAltitude(alt);
        return coordinate;
    }
}
