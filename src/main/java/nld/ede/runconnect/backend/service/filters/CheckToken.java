// depricated

//package nld.ede.runconnect.backend.service.filters;
//
//import com.google.gson.Gson;
//import nld.ede.runconnect.backend.dao.IUserDAO;
//import nld.ede.runconnect.backend.domain.User;
//import nld.ede.runconnect.backend.service.dto.ActivityDTO;
//import nld.ede.runconnect.backend.service.dto.RouteDTO;
//import nld.ede.runconnect.backend.service.tokens.TokenHashMap;
//
//import javax.inject.Inject;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.PreMatching;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.PathSegment;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//import javax.ws.rs.ext.Provider;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@PreMatching
//@Provider
//public class CheckToken implements ContainerRequestFilter {
//
//    private IUserDAO userDAO;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) {
//
//        UriInfo uriInfo = requestContext.getUriInfo();
//        String path = uriInfo.getPath();
//        List<PathSegment> pathSegments = uriInfo.getPathSegments();
//        String firstURIParameter = pathSegments.get(0).getPath();
//        MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();
//        String token = parameters.getFirst("token");
//        TokenHashMap tokenHashMap = TokenHashMap.getInstance();
//
//        try {
//            // Check if the user wants to login
//            if (path.equals("users") && requestContext.getMethod().equals("POST")) {
//                return;
//            }
//
//            // Check if the token exists
//            if (!tokenHashMap.doesExist(token)) {
//                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            }
//
//            // Check if http method is GET
//            if (requestContext.getMethod().equals("GET")) {
//                return;
//            }
//
//            // Check if the path goes to route and user is admin
//            String userEmail = tokenHashMap.getEmail(token);
//            if (firstURIParameter.equals("routes") && userDAO.CheckIfMailIsAdmin(userEmail)) {
//                return;
//            }
//
//            // Check if the path goes to activity and user is the uploader of the activity
//            if (firstURIParameter.equals("activities")) {
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
//                return;
//            }
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//
//        } catch (SQLException throwables) {
//            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
//        }
//    }
//
//    /**
//     * Injects and sets the user DAO.
//     *
//     * @param userDAO The DAO.
//     */
//    @Inject
//    public void setUserDAO(IUserDAO userDAO) {
//        this.userDAO = userDAO;
//    }
//}
