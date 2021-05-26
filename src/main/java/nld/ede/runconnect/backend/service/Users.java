package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IUserDAO;
import nld.ede.runconnect.backend.service.dto.UserDTO;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("users")
public class Users
{
    private IUserDAO userDAO;

    /**
     * Searches for users based on a search value.
     * @param searchValue The value to search for.
     * @return The response code with body.
     */
    @GET
    @Path("/find/{search-value}")
    public Response findUser(@PathParam("search-value") String searchValue) throws SQLException {
        ArrayList<UserDTO> users = DTOconverter
            .domainsToUserDTOs(userDAO.searchForUsers(searchValue));

        // Return OK if request goes through.
        // This suggests that the request went well, even if the result is empty.
        return Response.status(Response.Status.OK).entity(users).build();
    }

    /**
     * Injects and sets the user DAO.
     * @param userDAO The DAO.
     */
    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
