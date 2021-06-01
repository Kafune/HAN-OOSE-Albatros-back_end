package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.IUserDAO;
import nld.ede.runconnect.backend.domain.User;
import nld.ede.runconnect.backend.service.dto.UserDTO;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;
import nld.ede.runconnect.backend.service.helpers.GoogleIdVerifier;
import nld.ede.runconnect.backend.service.tokens.TokenHashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("users")
public class Users
{
    private IUserDAO userDAO;
    private GoogleIdVerifier googleIdVerifier;

    /**
     * Searches for users based on a search value.
     *
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
     *
     * @param followerId The user to follow the followee in the path parameter.
     * @return A response with status code 200 if successful, 400 if not successful.
     * @throws SQLException Exception if SQL fails.
     */
    @POST
    @Path("/{follower-id}/follows/{followee-id}")
    public Response follow(@PathParam("follower-id") int followerId, @PathParam("followee-id") int followeeId) throws SQLException
    {
        if (followeeId != followerId && userDAO.toggleFollow(true, followerId, followeeId)) {
            return Response.status(200).build();
        }

        return Response.status(400).build();
    }

    /**
     * Unfollows a user based on a user ID.
     *
     * @param followerId The user to follow the followee in the path parameter.
     * @return A response with status code 200 if successful, 400 if not successful.
     * @throws SQLException Exception if SQL fails.
     */
    @DELETE
    @Path("/{follower-id}/unfollows/{followee-id}")
    public Response unfollow(@PathParam("follower-id") int followerId, @PathParam("followee-id") int followeeId) throws SQLException
    {
        if (followeeId != followerId && userDAO.toggleFollow(false, followerId, followeeId)) {
            return Response.status(200).build();
        }

        return Response.status(400).build();
    }

    /**
     * Registers a user.
     *
     * @param user The user to register.
     * @return The response code.
     * @throws SQLException Exception if SQL fails.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) throws SQLException {

        boolean isExistingUserInGoogle = googleIdVerifier.verifyGoogleId(user);

        if (isExistingUserInGoogle) {
            boolean registered = userDAO.registerUser(user);
            User userInDatabase = userDAO.findUser(user.getEmailAddress());
            UserDTO userDTO = DTOconverter.domainToUserDTO(userInDatabase);
            TokenHashMap tokenHashMap = TokenHashMap.getInstance();
            userDTO.token = tokenHashMap.addToken(userDTO.emailAddress);
            if (registered) {
                return Response.status(201).entity(userDTO).build();
            }
            return Response.status(200).entity(userDTO).build();

        }
        return Response.status(404).build();
    }

    /**
     * Injects and sets the user DAO.
     *
     * @param userDAO The DAO.
     */
    @Inject
    public void setUserDAO(IUserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    @Inject
    public void setGoogleIdVerifier(GoogleIdVerifier googleIdVerifier) {
        this.googleIdVerifier = googleIdVerifier;
    }
}
