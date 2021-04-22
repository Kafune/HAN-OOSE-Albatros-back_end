package nld.ede.runconnect.backend.exceptions.ExceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
public class SQLExceptionMapper implements ExceptionMapper<SQLException> {


    @Override
    public Response toResponse(SQLException e) {
        return Response.status(
                Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
