package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IUserDAO;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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
    public Response findUser(String searchValue) {
        return null;
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
