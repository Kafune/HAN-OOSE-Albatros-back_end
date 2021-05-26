package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IRegistrationDAO;
import nld.ede.runconnect.backend.domain.User;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;
import nld.ede.runconnect.backend.service.dto.UserDTO;
import nld.ede.runconnect.backend.service.helpers.GoogleIdVerifier;

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
    private GoogleIdVerifier googleIdVerifier;


    /**
     * Registers a user.
     * @param user The user to register.
     * @return The response code.
     * @throws SQLException Exception if SQL fails.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) throws SQLException {

        boolean isExistingUserInGoogle = googleIdVerifier.verifyGoogleId(user);

        if (isExistingUserInGoogle) {
            boolean registered = registrationDAO.registerUser(user);
            User userInDatabase = registrationDAO.findUser(user.getEmailAddress());
            UserDTO userDTO = DTOconverter.domainToUserDTO(userInDatabase);
            if (registered) {
                return Response.status(201).entity(userDTO).build();
            }
            return Response.status(200).entity(userDTO).build();

        }
        return Response.status(404).build();
    }


    /**
     * Injects and sets the registration DAO.
     * @param registrationDAO The DAO.
     */
    @Inject
    public void setRegistrationDAO(IRegistrationDAO registrationDAO) {
        this.registrationDAO = registrationDAO;
    }

    /**
     * Injects and sets the google verifier.
     * @param googleIdVerifier The verifier.
     */
    @Inject
    public void setGoogleIdVerifier(GoogleIdVerifier googleIdVerifier) {
        this.googleIdVerifier = googleIdVerifier;
    }
}
