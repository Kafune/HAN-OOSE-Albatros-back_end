package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IUserDAO;
import nld.ede.runconnect.backend.domain.User;
import nld.ede.runconnect.backend.service.dto.UserDTO;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    @POST
    @Path("/find")
    public Response findUser(String searchValue) throws SQLException
    {
        ArrayList<User> users = userDAO.searchForUsers(searchValue);
        ArrayList<UserDTO> userDTOs = DTOconverter.domainsToUserDTOs(users);

        if (userDTOs.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(userDTOs).build();
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
