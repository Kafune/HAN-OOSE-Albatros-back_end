package nld.ede.runconnect.backend.service;
//custom imports

import nld.ede.runconnect.backend.dao.IRouteDAO;
import nld.ede.runconnect.backend.domain.Route;
import nld.ede.runconnect.backend.service.dto.HelloworldDTO;
import nld.ede.runconnect.backend.service.dto.RouteDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("routes")
public class Routes {

    IRouteDAO routesDAO;


    @POST
    @Path("/")
    public Response makeRoute(@QueryParam("token") String token, String RequestBody) {
        //build body to object
        RouteDTO newPlaylist;

        newPlaylist = DTOconverter.JSONToPlaylistDTO(body);

        //edit that playlist
        PlaylistsDAO.updatePlaylist(newPlaylist.name, newPlaylist.id);
        //nothing
        return Response.status(200).build();
    }

    @Inject
    public void setRoutesDAO(IRouteDAO routesDAO) {
        this.routesDAO = routesDAO;
    }
}
