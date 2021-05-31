package nld.ede.runconnect.backend.service.filters.firewall;

import nld.ede.runconnect.backend.dao.IUserDAO;
import nld.ede.runconnect.backend.service.tokens.TokenHashMap;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public class FirewallRoutes implements IFirewall{
    private IUserDAO userDAO;

    @Override
    public void rules(ContainerRequestContext requestContext, List<PathSegment> pathSegments, MultivaluedMap<String, String> parameters) throws SQLException {
        String token = parameters.getFirst("token");
        TokenHashMap tokenHashMap = TokenHashMap.getInstance();
        String userEmail = tokenHashMap.getEmail(token);

        //Path("/")
        //makeRoute
        if(requestContext.getMethod().equals("POST") && tokenHashMap.doesExist(token)){
            if(userDAO.CheckIfMailIsAdmin(userEmail)) {
                return;
            }
        }

        //Path("/")
        //findAllRoutes
        if(requestContext.getMethod().equals("GET") && tokenHashMap.doesExist(token)){
            return;
        }

        requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
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
