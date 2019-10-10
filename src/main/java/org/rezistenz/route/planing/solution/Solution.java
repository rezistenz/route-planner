package org.rezistenz.route.planing.solution;

import java.util.List;

public class Solution {

    private List<Route> routes;

    public Solution(final List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(final List<Route> routes) {
        this.routes = routes;
    }

}
