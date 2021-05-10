package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.ActivityDAO;
import nld.ede.runconnect.backend.domain.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivitiesTest {

    private Activities activities;

    @BeforeEach
    void setUp() {
        activities = new Activities();
    }

    @Test
    void addActivityTest() throws SQLException {
        int statusCodeExpected = 201;
        String json = "{\n" +
                "    \"routeId\": 1,\n" +
                "    \"userId\": 1,\n" +
                "    \"point\": 20,\n" +
                "    \"duration\": 100000,\n" +
                "    \"tempo\": 12,\n" +
                "    \"distance\": 19\n" +
                "}";

        //mock
        var activity = new Activity();
        ActivityDAO activityDAO = mock(ActivityDAO.class);

        activities.setActivityDAO(activityDAO);

        Response response = activities.addActivity(json);

        assertEquals(statusCodeExpected, response.getStatus());

    }
}
