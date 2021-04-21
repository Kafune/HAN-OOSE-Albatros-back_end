package nld.ede.runconnect.backend.dao;

import nld.ede.runconnect.backend.domain.Route;

import java.util.List;

public interface IRouteDAO {
    List<Route> findAllRoutes();

    void addNewRoute(Route newRoute);
}