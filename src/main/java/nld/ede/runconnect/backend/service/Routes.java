package nld.ede.runconnect.backend.service;
//custom imports

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.domain.Route;
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
    public Response makeRoute(String RequestBody) {
        //build body to object
        RouteDTO newRouteDTO;

        newRouteDTO = DTOconverter.JSONToRouteDTO(RequestBody);

        Route newRoute = DTOconverter.RouteDTOToDomainRoute(newRouteDTO);

        routesDAO.addNewRoute(newRoute);

        return Response.status(201).entity(newRoute).build();
    }

    @Inject
    public void setRoutesDAO(IRouteDAO routesDAO) {
        this.routesDAO = routesDAO;
    }
}
