package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Route;

import java.sql.SQLException;
import java.util.List;

public interface IRouteDAO {
    List<Route> getAllRoutes();
    void addNewRoute(Route newRoute) throws SQLException;
}
