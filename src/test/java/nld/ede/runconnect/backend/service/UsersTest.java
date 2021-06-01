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
    private Users users;
    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        users = new Users();
        userDAO = mock(UserDAO.class);
    }

    @Test
    void findUserTest() {
        User user = getUser();
        String search = "Alras";
        ArrayList<User> usersReturn = new ArrayList<>();
        usersReturn.add(user);

        try {
            // Arrange
            when(userDAO.searchForUsers(search)).thenReturn(usersReturn);
            users.setUserDAO(userDAO);

            // Act
            Response expectedResponse = users.searchForUser(search);
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
    void followTestSuccess() {
        try {
            // Arrange
            when(userDAO.toggleFollow(true, 1, 2)).thenReturn(true);
            users.setUserDAO(userDAO);

            // Act
            Response response = users.follow(1, 2);

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
            when(userDAO.toggleFollow(true, 1, 2)).thenReturn(false);
            users.setUserDAO(userDAO);

            // Act
            Response response = users.follow(1, 2);

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
            when(userDAO.toggleFollow(false, 1, 2)).thenReturn(true);
            users.setUserDAO(userDAO);

            // Act
            Response response = users.unfollow(1, 2);

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
            when(userDAO.toggleFollow(false, 1, 2)).thenReturn(false);
            users.setUserDAO(userDAO);

            // Act
            Response response = users.unfollow(1, 2);

            // Assert
            assertEquals(response.getStatus(), 400);
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
