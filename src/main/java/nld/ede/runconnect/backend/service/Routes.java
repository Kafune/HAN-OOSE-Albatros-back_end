package nld.ede.runconnect.backend.service;
//custom imports

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;
import nld.ede.runconnect.backend.service.dto.RouteDTO;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

@Path("routes")
public class Routes {

    private IRouteDAO routesDAO;


    @POST
    @Path("/")
    public Response makeRoute(String RequestBody) throws SQLException {
        //build body to object
        RouteDTO newRouteDTO = DTOconverter.JSONToRouteDTO(RequestBody);

        //build dto to domain object
        Route newRoute = DTOconverter.RouteDTOToDomainRoute(newRouteDTO);

        //add to the database
        routesDAO.addNewRoute(newRoute);

        return Response.status(201).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRoutes() throws SQLException {

        List<Route> routesInDatabase = routesDAO.getAllRoutes();

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
