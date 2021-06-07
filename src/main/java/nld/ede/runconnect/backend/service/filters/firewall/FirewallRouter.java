package nld.ede.runconnect.backend.service.filters.firewall;

import nld.ede.runconnect.backend.dao.IUserDAO;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
import java.util.List;

@PreMatching
@Provider
public class FirewallRouter implements ContainerRequestFilter {
    IFirewall firewall;
    private IUserDAO userDAO;

    /**
     * This filter handles the total firewall. If done correctly all routes will be covered.
     * @param requestContext
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {
        UriInfo uriInfo = requestContext.getUriInfo();
        List<PathSegment> pathSegments = uriInfo.getPathSegments();
        String firstURIParameter = pathSegments.get(0).getPath();
        MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();
        switch (firstURIParameter) {
            case "activities":
                firewall = new FirewallActivities(userDAO);
                break;
            case "hello":
                return;
                // helloworld doesn't do anything but return. This is because helloworld doesnt need a firewall.
                // Everybody should be able access it.
            case "routes":
                firewall = new FirewallRoutes(userDAO);
                break;
            case "segments":
                firewall = new FirewallSegments();
                break;
            case "users":
                firewall = new FirewallUsers();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + firstURIParameter);
        }

        try {
            firewall.rules(requestContext, pathSegments, parameters);
        } catch (SQLException throwables) {
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }
    /**
     * Injects and sets the user DAO.
     *
     * @param userDAO The DAO.
     */
    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
