package org.rezistenz.route.planing.app;

import org.apache.commons.lang3.tuple.Pair;
import org.rezistenz.route.planing.alg.Algorithm;
import org.rezistenz.route.planing.alg.DumpAlgorithm;
import org.rezistenz.route.planing.config.RoutesPlanConfig;
import org.rezistenz.route.planing.model.Distance;
import org.rezistenz.route.planing.model.Location;
import org.rezistenz.route.planing.solution.Cost;
import org.rezistenz.route.planing.solution.Solution;

public class RoutesPlanApp {

    private final RoutesPlanConfig config;

    public RoutesPlanApp(final RoutesPlanConfig config) {
        this.config = config;
    }

    public void routesPlanExec() {
        final Algorithm algorithm = new DumpAlgorithm(config);

        final Solution solution = algorithm.buildSolution();

        solution.getRoutes().forEach(route -> {
            final Cost cost = route.buildCost();

            System.out.println(cost.getTime() / 60 / 60);
            System.out.println(cost.getSum());

            System.out.println(route.getCar().getSum());
            System.out.println(route.getCar().getMaxSum());

            System.out.println(route.getCar().getStartLocation());
            System.out.println(route.getCar().getEndLocation());

            System.out.println(route.getDistanceToLocations().size());
            for (final Pair<Distance, Location> item: route.getDistanceToLocations()) {
                System.out.println(item);
            }
        });
    }




}
