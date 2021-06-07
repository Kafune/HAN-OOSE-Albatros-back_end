package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.ISegmentDAO;
import nld.ede.runconnect.backend.domain.Segment;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;
import nld.ede.runconnect.backend.service.dto.SegmentDTO;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("segments")
public class Segments {
    private ISegmentDAO segmentDAO;

    /**
     * Finds all the segments of a specific route.
     * @param id The route ID.
     * @return The response code with body.
     * @throws SQLException Exception if SQL fails.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSegmentsOfRoute(@PathParam("id") int id) throws SQLException {
        List<Segment> segmentListInDatabase = segmentDAO.getSegmentsOfRoute(id);

        if (segmentListInDatabase == null) {
            return Response.status(404).build();
        }
        List<SegmentDTO> segmentDTOList = new ArrayList<>();
        for (Segment item : segmentListInDatabase) {
            segmentDTOList.add(DTOconverter.domainToSegmentDTO(item));
        }
        return Response.ok().entity(segmentDTOList).build();
    }


    /**
     * Injects and sets the segment DAO.
     * @param segmentDAO The DAO.
     */
    @Inject
    public void setSegmentDAO(ISegmentDAO segmentDAO) {
        this.segmentDAO = segmentDAO;
    }
}
