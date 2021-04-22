package nld.ede.runconnect.backend.exceptions.ExceptionMappers;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLExceptionMapperTest {


    @Test
    public void SQLExceptionMapper(){
        int statusCodeExpected = 500;

        var mapper = new SQLExceptionMapper();
        Response response =  mapper.toResponse(new SQLException());

        assertEquals(statusCodeExpected,response.getStatus());
    }
}


