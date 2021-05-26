package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IActivityDAO;
import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.service.dto.ActivityDTO;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;

import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("activities")
public class Activities {

    private IActivityDAO activityDAO;

    /**
     * Adds an activity.
     * @param activityDTO The data on which to add.
     * @return The response of the request.
     * @throws SQLException Exception if SQL fails.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addActivity(ActivityDTO activityDTO) throws SQLException {
        Activity newActivity = DTOconverter.ActivityDTOToDomainActivity(activityDTO);
        activityDAO.addNewActivity(newActivity);

        return Response.status(201).build();
    }

    @Inject
    public void setActivityDAO(IActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

}
