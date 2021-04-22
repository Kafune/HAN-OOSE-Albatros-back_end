package nld.ede.runconnect.backend.service;
//custom imports

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.dto.DTOconverter;
import nld.ede.runconnect.backend.service.dto.RouteDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("routes")
public class Routes {

    IRouteDAO routesDAO;


    @POST
    @Path("/")
    public Response makeRoute(@QueryParam("token") String token, String RequestBody) {
        //build body to object
        RouteDTO newRoute;

        newRoute = DTOconverter.JSONToRouteDTO(RequestBody);


        return Response.status(200).entity(newRoute).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRoutes() {
        List<Route> routesInDatabase = routesDAO.findAllRoutes();

        if (routesInDatabase == null) {
            return Response.status(404).build();
        }
        List<RouteDTO> routeDTOList = new ArrayList<>();

        for (Route item : routesInDatabase) {
            routeDTOList.add(DTOconverter.domainToRouteDTO(item));
        }
        return Response.ok().entity(routeDTOList).build();
    }


    @Inject
    public void setRoutesDAO(IRouteDAO routesDAO) {
        this.routesDAO = routesDAO;
    }
}
