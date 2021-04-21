package nld.ede.runconnect.backend.service;
//custom imports

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.service.dto.DTOconverter;
import nld.ede.runconnect.backend.service.dto.RouteDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

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

    @Inject
    public void setRoutesDAO(IRouteDAO routesDAO) {
        this.routesDAO = routesDAO;
    }
}
