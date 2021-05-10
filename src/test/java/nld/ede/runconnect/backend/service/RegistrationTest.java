package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.RegistrationDAO;
import nld.ede.runconnect.backend.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class RegistrationTest {

    private Registration sut;
    private RegistrationDAO registrationDAOMock;

    @BeforeEach
    public void setup() {
        sut = new Registration();
        registrationDAOMock = mock(RegistrationDAO.class);
    }

    @Test
    public void registerUserCallsMethodInDAOTest() {
        User user = new User();
        try {
            when(registrationDAOMock.registerUser(user)).thenReturn(true);
            sut.setRegistrationDAO(registrationDAOMock);

            sut.registerUser(user);

            verify(registrationDAOMock).registerUser(user);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void registerUserReturns201IfUserRegistered() {
        User user = new User();
        try {
            when(registrationDAOMock.registerUser(user)).thenReturn(true);
            sut.setRegistrationDAO(registrationDAOMock);

            Response response = sut.registerUser(user);
            int actualResponseStatus = response.getStatus();

            assertEquals(201, actualResponseStatus);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void registerUserReturns200IfUserFoundInDatabase() {
        User user = new User();
        try {
            when(registrationDAOMock.registerUser(user)).thenReturn(false);
            sut.setRegistrationDAO(registrationDAOMock);

            Response response = sut.registerUser(user);
            int actualResponseStatus = response.getStatus();

            assertEquals(200, actualResponseStatus);
        } catch (SQLException e) {
            fail();
        }
    }
}
