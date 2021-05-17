package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.ActivityDAO;
import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.service.dto.ActivityDTO;
import nld.ede.runconnect.backend.service.dto.DTOconverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

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

        ActivityDTO activityDTO = new ActivityDTO();

        //mock
        ActivityDAO activityDAO = mock(ActivityDAO.class);
        Activity activity = new Activity();

        try (MockedStatic<DTOconverter> utilities = Mockito.mockStatic(DTOconverter.class)) {
            utilities.when(() -> DTOconverter.ActivityDTOToDomainActivity(activityDTO))
                    .thenReturn(activity);
        }

        activities.setActivityDAO(activityDAO);

        Response response = activities.addActivity(activityDTO);

        assertEquals(statusCodeExpected, response.getStatus());

    }
}
