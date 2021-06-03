package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.UserDAO;
import nld.ede.runconnect.backend.domain.User;
import nld.ede.runconnect.backend.service.dto.UserDTO;
import nld.ede.runconnect.backend.service.helpers.GoogleIdVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class UsersTest
{
    private static final int USER_ID = 3;
    private static final String GOOGLE_ID = "12122143";
    private Users sut;
    private UserDAO userDAOMock;

    @BeforeEach
    public void setup() {
        sut = new Users();
        userDAOMock = mock(UserDAO.class);
    }

    @Test
    void findUserTest() {
        User user = getUser();
        String search = "Alras";
        ArrayList<User> usersReturn = new ArrayList<>();
        usersReturn.add(user);

        try {
            // Arrange
            when(userDAOMock.searchForUsers(search)).thenReturn(usersReturn);
            sut.setUserDAO(userDAOMock);

            // Act
            Response expectedResponse = sut.searchForUser(search);
            ArrayList<UserDTO> usersFromResponse = (ArrayList<UserDTO>) expectedResponse.getEntity();

            // Assert
            assertEquals(usersFromResponse.get(0).userId, usersReturn.get(0).getUserId());
            assertEquals(usersFromResponse.get(0).emailAddress, usersReturn.get(0).getEmailAddress());
            assertEquals(usersFromResponse.get(0).username, usersReturn.get(0).getUsername());
            assertEquals(usersFromResponse.get(0).firstName, usersReturn.get(0).getFirstName());
            assertEquals(usersFromResponse.get(0).lastName, usersReturn.get(0).getLastName());
            assertEquals(usersFromResponse.get(0).imageUrl, usersReturn.get(0).getImageUrl());
            assertEquals(usersFromResponse.get(0).totalScore, usersReturn.get(0).getTotalScore());
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void registerUserCallsMethodInDAOTest() {
        User user = getUser();

        try {
            GoogleIdVerifier googleIdVerifierMock = mock(GoogleIdVerifier.class);
            when(googleIdVerifierMock.verifyGoogleId(user)).thenReturn(true);
            sut.setGoogleIdVerifier(googleIdVerifierMock);

            when(userDAOMock.registerUser(user)).thenReturn(true);
            when(userDAOMock.findUser(user.getEmailAddress())).thenReturn(user);
            sut.setUserDAO(userDAOMock);

            sut.registerUser(user);

            verify(userDAOMock).registerUser(user);

        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void registerUserReturns201IfUserRegistered() {
        User user = getUser();
        try {
            GoogleIdVerifier googleIdVerifierMock = mock(GoogleIdVerifier.class);
            when(googleIdVerifierMock.verifyGoogleId(user)).thenReturn(true);
            sut.setGoogleIdVerifier(googleIdVerifierMock);

            when(userDAOMock.registerUser(user)).thenReturn(true);
            when(userDAOMock.findUser(user.getEmailAddress())).thenReturn(user);
            sut.setUserDAO(userDAOMock);

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
            GoogleIdVerifier googleIdVerifierMock = mock(GoogleIdVerifier.class);
            when(googleIdVerifierMock.verifyGoogleId(user)).thenReturn(true);
            sut.setGoogleIdVerifier(googleIdVerifierMock);

            when(userDAOMock.registerUser(user)).thenReturn(false);
            when(userDAOMock.findUser(user.getEmailAddress())).thenReturn(user);
            sut.setUserDAO(userDAOMock);

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
    @Test
    public void registerUserReturns404IfIdNotExistsInGoogle() {
        User user = getUser();
        try {
            GoogleIdVerifier googleIdVerifierMock = mock(GoogleIdVerifier.class);
            when(googleIdVerifierMock.verifyGoogleId(user)).thenReturn(false);
            sut.setGoogleIdVerifier(googleIdVerifierMock);


            Response response = sut.registerUser(user);
            int actualResponseStatus = response.getStatus();

            assertEquals(404, actualResponseStatus);

        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    void followTestSuccess() {
        try {
            // Arrange
            when(userDAOMock.toggleFollow(true, 1, 2)).thenReturn(true);
            sut.setUserDAO(userDAOMock);

            // Act
            Response response = sut.follow(1, 2);

            // Assert
            assertEquals(response.getStatus(), 200);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    void followTestFail() {
        try {
            // Arrange
            when(userDAOMock.toggleFollow(true, 1, 2)).thenReturn(false);
            sut.setUserDAO(userDAOMock);

            // Act
            Response response = sut.follow(1, 2);

            // Assert
            assertEquals(response.getStatus(), 400);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    void unfollowTestSuccess() {
        try {
            // Arrange
            when(userDAOMock.toggleFollow(false, 1, 2)).thenReturn(true);
            sut.setUserDAO(userDAOMock);

            // Act
            Response response = sut.unfollow(1, 2);

            // Assert
            assertEquals(response.getStatus(), 200);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    void unfollowTestFail() {
        try {
            // Arrange
            when(userDAOMock.toggleFollow(false, 1, 2)).thenReturn(false);
            sut.setUserDAO(userDAOMock);

            // Act
            Response response = sut.unfollow(1, 2);

            // Assert
            assertEquals(response.getStatus(), 400);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void isFollowingTest() {
        // TODO: Implement test.
    }

    @Test
    public void getFeedTest() {
        // TODO: Implement test.
    }

    @Test
    public void getByIdTest() {
        // TODO: Implement test.
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
