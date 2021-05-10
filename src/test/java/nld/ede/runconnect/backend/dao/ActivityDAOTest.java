package nld.ede.runconnect.backend.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActivityDAOTest {
    private ActivityDAO sut;

    @BeforeEach
    public void setSut() {
        sut = new ActivityDAO();
    }

    @Test
    public void saveActivity() {
        String sql = "INSERT INTO ACTIVITIES (ROUTEID, USERID, POINT, DURATION, TEMPO, DISTANCE) Values (?, ?, ?, ?, ?, ?)";
        try {

        } catch (Exception e) {
            fail(e);
        }
    }

}
