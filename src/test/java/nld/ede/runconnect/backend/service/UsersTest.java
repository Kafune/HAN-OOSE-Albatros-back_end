package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.UserDAO;
import nld.ede.runconnect.backend.domain.User;
import nld.ede.runconnect.backend.service.helpers.GoogleIdVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

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
        users = mock(Users.class);
        userDAO = mock(UserDAO.class);
    }

    @Test
    void findUserTest() {
        User user = getUser();
        String search = "Alras";
        ArrayList<User> usersReturn = new ArrayList<>();
        usersReturn.add(user);

        try {
            GoogleIdVerifier googleIdVerifierMock = mock(GoogleIdVerifier.class);
            when(googleIdVerifierMock.verifyGoogleId(user)).thenReturn(true);
            users.setUserDAO(userDAO);
            when(userDAO.searchForUsers(search)).thenReturn(usersReturn);

            users.findUser(search);

            verify(users).findUser(search);
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
