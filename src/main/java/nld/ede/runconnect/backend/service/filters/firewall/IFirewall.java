package nld.ede.runconnect.backend.service.filters.firewall;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import java.sql.SQLException;
import java.util.List;

public interface IFirewall {

    /**
     * This method handles the firewall of every route. Make sure you open secure every endpoint and add in the final
     * line of this method always a :
     * 'requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());'
     * This way, the status code 404 will stay intact. To let the request go to the equal endpoint you'll need to use a
     * return. For this reason it is very important to make sure all of the checks are cascading.
     * @param requestContext
     * @param pathSegments
     * @param parameters
     */
    void rules(ContainerRequestContext requestContext, List<PathSegment> pathSegments, MultivaluedMap<String, String> parameters) throws SQLException;
}
