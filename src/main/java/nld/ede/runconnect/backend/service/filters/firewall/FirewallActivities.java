package nld.ede.runconnect.backend.service.filters.firewall;

import com.google.gson.Gson;
import nld.ede.runconnect.backend.dao.IUserDAO;
import nld.ede.runconnect.backend.domain.User;
import nld.ede.runconnect.backend.service.dto.ActivityDTO;
import nld.ede.runconnect.backend.service.tokens.TokenHashMap;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class FirewallActivities implements IFirewall {
    private IUserDAO userDAO;

    FirewallActivities(IUserDAO userDAO){
        this.userDAO=userDAO;
    }

    @Override
    public void rules(ContainerRequestContext requestContext, List<PathSegment> pathSegments, MultivaluedMap<String, String> parameters) throws SQLException {
        String token = parameters.getFirst("token");
        TokenHashMap tokenHashMap = TokenHashMap.getInstance();

        //Path("/")
        //addActivity
        if ( tokenHashMap.doesExist(token) && requestContext.getMethod().equals("POST")) {
            //this wouldve worked if inputStreams were to be renewed. Sadly this cant be done. So we can't check if
            // the correct user is uploading his or hers activity.

//                String userEmail = tokenHashMap.getEmail(token);
//                // get userID with email
//                User user = userDAO.findUser(userEmail);
//                int userId = user.getUserId();
//
//                //get activity object from JSON body
//                String result = new BufferedReader(new InputStreamReader(requestContext.getEntityStream()))
//                        .lines().collect(Collectors.joining("\n"));
//                Gson JSON = new Gson();
//                var activity = JSON.fromJson(result, ActivityDTO.class);
//
//                if (activity.userId != userId) {
//                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//                }
                return;

        }


        requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
    }


}
