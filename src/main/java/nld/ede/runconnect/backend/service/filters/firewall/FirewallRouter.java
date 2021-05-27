package nld.ede.runconnect.backend.service.filters.firewall;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@PreMatching
@Provider
public class FirewallRouter implements ContainerRequestFilter {
    IFirewall firewall;

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
        String body = new BufferedReader(new InputStreamReader(requestContext.getEntityStream()))
                .lines().collect(Collectors.joining("\n"));

        switch (firstURIParameter) {
            case "Activities":
                firewall = new FirewallActivities();
                break;
            case "hello":
                return;
            case "routes":
                firewall = new FirewallRoutes();
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
            firewall.rules(requestContext, pathSegments, parameters, body);
        } catch (SQLException throwables) {
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }

    }
}
