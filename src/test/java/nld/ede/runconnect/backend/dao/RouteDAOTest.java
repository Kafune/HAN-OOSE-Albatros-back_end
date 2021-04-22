package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RouteDAOTest {

    private RouteDAO sut;

    @BeforeEach
    public void setSut() {
        sut = new RouteDAO();
    }

    @Test
    public void getAllRoutesTest() {
        String sql = "SELECT * FROM ROUTE";
        try {
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // setup classes
            sut.setDataSource(dataSource);

            /**** Act ****/
            List<Route> route = sut.getAllRoutes();

            /**** Assert ****/
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).executeQuery();
            assertEquals(0, route.size());

        } catch (Exception e) {
            fail();
        }
    }

}
