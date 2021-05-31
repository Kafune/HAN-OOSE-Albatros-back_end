package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IActivityDAO;
import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.service.dto.ActivityDTO;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

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

    /**
     *
     * @param userId id of a user
     * @return a response that containing a list of activities that belong to a user.
     * @throws SQLException if SQL fails.
     */
    @GET
    @Path("user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivities(@PathParam("userId") int userId) throws SQLException {
        ArrayList<ActivityDTO> activities = DTOconverter
                .domainsToActivityDTOs(activityDAO.getActivities(userId));

        if (activities.isEmpty()) {
            return Response.status(404).build();
        }

        return Response.status(200).entity(activities).build();
    }

    @Inject
    public void setActivityDAO(IActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

}
