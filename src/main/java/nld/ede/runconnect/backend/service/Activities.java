package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IActivityDAO;
import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.service.dto.ActivityDTO;
import nld.ede.runconnect.backend.service.dto.DTOconverter;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("activities")
public class Activities {

    IActivityDAO activityDAO;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addActivity(String requestBody) throws SQLException {
        ActivityDTO newActivityDTO = DTOconverter.JSONToActivityDTO(requestBody);
        Activity newActivity = DTOconverter.ActivityDTOToDomainActivity(newActivityDTO);
        activityDAO.addNewActivity(newActivity);

        return Response.status(201).build();
    }

    @Inject
    public void setActivityDAO(IActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

}
