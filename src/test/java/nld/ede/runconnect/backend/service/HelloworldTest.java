package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.service.dto.HelloworldDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class HelloworldTest {
    private Helloworld helloworld;
    @BeforeEach
    public void setUp() {
        helloworld = new Helloworld();
    }


    @Test
    void helloworld() {
        // Arrange
        int statusCodeExpected = 200;
        String textExpected = "hello world!";

        //mock

        // Act
        Response response = helloworld.helloworld();

        HelloworldDTO responseObject = (HelloworldDTO) response.getEntity();

        // Assert
        assertEquals(statusCodeExpected, response.getStatus());

        //test content
        assertEquals(textExpected, responseObject.text);

    }
}