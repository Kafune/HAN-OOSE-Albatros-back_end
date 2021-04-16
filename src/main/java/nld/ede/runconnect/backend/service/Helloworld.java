package nld.ede.runconnect.backend.service;
//custom imports
import nld.ede.runconnect.backend.service.dto.HelloworldDTO;
//java imports
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("hello")
public class Helloworld {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloworld() {
        HelloworldDTO helloworldDTO = new HelloworldDTO();
        helloworldDTO.text= "hello world!";
        var date = new Date();
        helloworldDTO.time = String.valueOf(date.getTime());
        return Response.status(200).entity(helloworldDTO).build();
    }
}
