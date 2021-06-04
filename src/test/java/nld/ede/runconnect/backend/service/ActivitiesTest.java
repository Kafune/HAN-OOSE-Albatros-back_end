package nld.ede.runconnect.backend.service;

import nld.ede.runconnect.backend.dao.ActivityDAO;
import nld.ede.runconnect.backend.domain.Activity;
import nld.ede.runconnect.backend.service.dto.ActivityDTO;
import nld.ede.runconnect.backend.service.helpers.DTOconverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivitiesTest {

    public  int route_id = 1;
    public int duration = 2;
    public  int distance = 4;
    public  int point = 4;
    public  int activity_id = 3;
    public  int user_id = 8;

    private Activities sut;

    @BeforeEach
    void setUp() {
        sut = new Activities();
    }

    @Test
    void addActivityTest() {
        int statusCodeExpected = 201;

        ActivityDTO activityDTO = new ActivityDTO();

        //mock
        ActivityDAO activityDAO = mock(ActivityDAO.class);
        Activity activity = new Activity();

        try (MockedStatic<DTOconverter> utilities = Mockito.mockStatic(DTOconverter.class)) {
            utilities.when(() -> DTOconverter.ActivityDTOToDomainActivity(activityDTO))
                    .thenReturn(activity);
        }

        sut.setActivityDAO(activityDAO);

        Response response = null;
        try {
            response = sut.addActivity(activityDTO);
        } catch (SQLException e) {
            fail();
        }

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void getActivitiesReturnsResponseWithEntityIfFound() {
        int userId  = 1;
        List<Activity> activities = getListOfActivities();

        List<ActivityDTO> activityDTOS = getListOfActivityDTOs();
        ActivityDAO activityDAOMock = mock(ActivityDAO.class);
        try {
            when(activityDAOMock.getActivities(userId)).thenReturn(activities);

            try (MockedStatic<DTOconverter> utilities = Mockito.mockStatic(DTOconverter.class)) {
                utilities.when(() -> DTOconverter.domainsToActivityDTOs(activities))
                        .thenReturn(activityDTOS);
            }
            sut.setActivityDAO(activityDAOMock);

            Response expectedResponse = sut.getActivities(userId);

            assertEquals(expectedResponse.getStatus(), 200);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getActivitiesReturns404IfNoResults() {
        int userId  = 1;
        List<Activity> activities = new ArrayList<>();

        List<ActivityDTO> activityDTOS = new ArrayList<>();
        ActivityDAO activityDAOMock = mock(ActivityDAO.class);
        try {
            when(activityDAOMock.getActivities(userId)).thenReturn(activities);


            sut.setActivityDAO(activityDAOMock);

            Response expectedResponse = sut.getActivities(userId);

            assertEquals(expectedResponse.getStatus(), 204);
        }
        catch (SQLException e) {
            fail();
        }
    }

    private List<ActivityDTO> getListOfActivityDTOs() {
        ActivityDTO activity = new ActivityDTO();
        activity.routeId = route_id;
        activity.distance = distance;
        activity.duration = duration;
        activity.point = point;
        activity.activityId = activity_id;
        activity.userId = user_id;

        List<ActivityDTO> activities = new ArrayList<>();
        activities.add(activity);
        return activities;
    }

    private List<Activity> getListOfActivities() {
        Activity activity = new Activity();
        activity.setRouteId(route_id);
        activity.setDuration(duration);
        activity.setDistance(distance);
        activity.setPoint(point);
        activity.setActivityId(activity_id);
        activity.setUserId(user_id);

        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        return activities;
    }
}
