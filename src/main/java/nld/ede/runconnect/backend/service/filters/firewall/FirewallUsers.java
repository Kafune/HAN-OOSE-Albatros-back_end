package nld.ede.runconnect.backend.service.filters.firewall;

import nld.ede.runconnect.backend.service.tokens.TokenHashMap;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import java.util.List;

public class FirewallUsers  implements IFirewall{


    @Override
    public void rules(ContainerRequestContext requestContext, List<PathSegment> pathSegments, MultivaluedMap<String, String> parameters, String body) {
        String token = parameters.getFirst("token");
        TokenHashMap tokenHashMap = TokenHashMap.getInstance();

        //Path("/")
        //registerUser
        if(requestContext.getMethod().equals("POST") && pathSegments.get(1) ==null){
            return;
        }

        //Path("/find/{search-value}")
        //searchForUser
        if(pathSegments.get(1).toString().equals("find") && tokenHashMap.doesExist(token)){
            return;
        }

        requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
    }
}
