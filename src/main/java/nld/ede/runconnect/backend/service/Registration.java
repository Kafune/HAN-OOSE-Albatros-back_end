package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IRegistrationDAO;
import nld.ede.runconnect.backend.domain.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("registration")
public class Registration {

    private IRegistrationDAO registrationDAO;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) throws SQLException {
        boolean registered = registrationDAO.registerUser(user);
        if (registered) {
            return Response.status(201).build();
        } else {
            return Response.status(200).build();
        }
    }

    @Inject
    public void setRegistrationDAO(IRegistrationDAO registrationDAO) {
        this.registrationDAO = registrationDAO;
    }
}
