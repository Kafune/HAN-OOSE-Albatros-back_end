package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IUserDAO;
import nld.ede.runconnect.backend.service.dto.UserDTO;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    public Response searchForUser(@PathParam("search-value") String searchValue) throws SQLException
    {
        ArrayList<UserDTO> users = DTOconverter
            .domainsToUserDTOs(userDAO.searchForUsers(searchValue));

        if (users.size() == 0) {
            return Response.status(204).entity(users).build();
        }

        return Response.status(200).entity(users).build();
    }

    /**
     * Follows a user based on a user ID.
     * @param followerId The user to follow the followee in the path parameter.
     * @return A response with status code 200 if successful, 400 if not successful.
     * @throws SQLException Exception if SQL fails.
     */
    @POST
    @Path("/{follower-id}/follow/{followee-id}")
    public Response follow(@PathParam("follower-id") int followerId, @PathParam("followee-id") int followeeId) throws SQLException
    {
        if (followeeId != followerId && userDAO.toggleFollow(true, followerId, followeeId)) {
            return Response.status(200).build();
        }

        return Response.status(400).build();
    }

    /**
     * Unfollows a user based on a user ID.
     * @param followerId The user to follow the followee in the path parameter.
     * @return A response with status code 200 if successful, 400 if not successful.
     * @throws SQLException Exception if SQL fails.
     */
    @POST
    @Path("/{follower-id}/unfollow/{followee-id}")
    public Response unfollow(@PathParam("follower-id") int followerId, @PathParam("followee-id") int followeeId) throws SQLException
    {
        if (followeeId != followerId && userDAO.toggleFollow(false, followerId, followeeId)) {
            return Response.status(200).build();
        }

        return Response.status(400).build();
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
