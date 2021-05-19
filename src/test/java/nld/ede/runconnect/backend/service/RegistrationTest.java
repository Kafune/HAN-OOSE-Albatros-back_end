package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.RegistrationDAO;
import nld.ede.runconnect.backend.domain.User;
import nld.ede.runconnect.backend.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class RegistrationTest {

    private static final int USER_ID = 3;
    private static final String GOOGLE_ID = "12122143";
    private Registration sut;
    private RegistrationDAO registrationDAOMock;

    @BeforeEach
    public void setup() {
        sut = new Registration();
        registrationDAOMock = mock(RegistrationDAO.class);
    }

    @Test
    public void registerUserCallsMethodInDAOTest() {
        User user = getUser();
        try {
            when(registrationDAOMock.registerUser(user)).thenReturn(true);
            when(registrationDAOMock.findUser(GOOGLE_ID)).thenReturn(user);
            sut.setRegistrationDAO(registrationDAOMock);

            sut.registerUser(user);

            verify(registrationDAOMock).registerUser(user);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void registerUserReturns201IfUserRegistered() {
        User user = getUser();
        try {
            when(registrationDAOMock.registerUser(user)).thenReturn(true);
            when(registrationDAOMock.findUser(GOOGLE_ID)).thenReturn(user);
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
        User user = getUser();
        try {
            when(registrationDAOMock.registerUser(user)).thenReturn(false);
            when(registrationDAOMock.findUser(GOOGLE_ID)).thenReturn(user);
            sut.setRegistrationDAO(registrationDAOMock);

            Response response = sut.registerUser(user);
            int actualResponseStatus = response.getStatus();
            UserDTO actualUserDTO = (UserDTO) response.getEntity();

            assertEquals(200, actualResponseStatus);

            assertEquals(user.getUserId(), actualUserDTO.userId);
            assertEquals(user.getFirstName(), actualUserDTO.firstName);
            assertEquals(user.getLastName(), actualUserDTO.lastName);
            assertEquals(user.getEmailAddress(), actualUserDTO.emailAddress);
            assertEquals(user.getUsername(), actualUserDTO.username);
            assertEquals(user.getTotalScore(), actualUserDTO.totalScore);
            assertEquals(user.getImageUrl(), actualUserDTO.imageUrl);
        } catch (SQLException e) {
            fail();
        }
    }
    private User getUser() {
        User user = new User();
        user.setUserId(USER_ID);
        user.setFirstName("Alrasheed");
        user.setLastName("Obada");
        user.setEmailAddress("Mail test adres");
        user.setUsername("Ik heb geen zin meer");
        user.setTotalScore(32);
        user.setGoogleId(GOOGLE_ID);
        user.setImageUrl("Url.com");

        return user;
    }
}
