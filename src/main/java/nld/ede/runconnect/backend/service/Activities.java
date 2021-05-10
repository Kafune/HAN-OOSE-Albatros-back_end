package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IActivityDAO;
import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.service.dto.ActivityDTO;
import nld.ede.runconnect.backend.service.dto.DTOconverter;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class Activities {

    IActivityDAO activityDAO;

    @POST

    @Produces(MediaType.APPLICATION_JSON)
    public Response makeActivity(String requestBody) throws SQLException {
        ActivityDTO newActivityDTO = DTOconverter.JSONToActivityDTO(requestBody);

        Activity newActivity = DTOconverter.ActivityDTOToDomainActivity(newActivityDTO);

        activityDAO.addNewActivity(newActivity);

        return Response.status(201).build();
    }

    public void setActivityDAO(IActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

}
