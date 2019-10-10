package org.rezistenz.route.planing.alg;

import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graphs;
import org.rezistenz.route.planing.config.RoutesPlanConfig;
import org.rezistenz.route.planing.model.Car;
import org.rezistenz.route.planing.model.Distance;
import org.rezistenz.route.planing.model.Location;
import org.rezistenz.route.planing.model.Path;
import org.rezistenz.route.planing.solution.Route;
import org.rezistenz.route.planing.solution.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DumpAlgorithm implements Algorithm {

    private final RoutesPlanConfig config;

    public DumpAlgorithm(final RoutesPlanConfig config) {
        this.config = config;
    }

    @Override
    public Solution buildSolution() {
        final Car car = config.getCars().get(0);

        final Route route = new Route();

        route.setCar(car);
        route.getDistanceToLocations().add(Pair.of(new Distance(0.0, 0.0, 0.0), car.getStartLocation()));

        final Set<Location> visited = new HashSet<>();

        visited.add(car.getStartLocation());
        visited.add(car.getEndLocation());

        double planigTime = 0.0; // sec

        Location fromLocation = car.getStartLocation();

        Location lastVisitedLocation = null;

        while (!car.getEndLocation().equals(lastVisitedLocation)) {

            // TODO прервать если времени работы не осталось.

            final Optional<Path> firstToLocationPath = getFirstToLocationPath(
                    car,
                    visited,
                    fromLocation
            );

            if (!firstToLocationPath.isPresent()) {
                // сдача в конечную точку
                planigTime += visitEndLocation(route, visited, fromLocation, car.getEndLocation());

                lastVisitedLocation = car.getEndLocation();
            } else {
                final Path toLocationPath = firstToLocationPath.orElseThrow(() -> new RuntimeException("toLocationDistance not found"));

                final Location toLocation = toLocationPath.getTo();

                final int sumAfterVisit = getSumAfterVisit(car, toLocation);

                if (sumAfterVisit > car.getMaxSum()) {
                    // сдача в конечную точку
                    planigTime += visitEndLocation(route, visited, fromLocation, car.getEndLocation());

                    lastVisitedLocation = car.getEndLocation();
                } else {
                    // Обслуживание клиента
                    planigTime += visitClient(car, route, visited, toLocationPath, toLocation);

                    lastVisitedLocation = toLocation;
                }

            }

            fromLocation = lastVisitedLocation;
        }

        final ArrayList<Route> routes = new ArrayList<>();

        routes.add(route);

        return new Solution(routes);
    }

    private double visitClient(
            final Car car,
            final Route route,
            final Set<Location> visited,
            final Path toLocationPath,
            final Location toLocation
    ) {
        car.setSum(car.getSum() + toLocation.getSum());

        final Distance toLocationDistance = toLocationPath.getDistance();

        route.getDistanceToLocations().add(Pair.of(toLocationDistance, toLocation));

        visited.add(toLocation);

        return toLocationDistance.getTimeWithLoadFactor()
               + toLocation.getServiceTime();
    }

    private double visitEndLocation(
            final Route route,
            final Set<Location> visited,
            final Location fromLocation,
            final Location toLocation
    ) {
        final Distance toLocationDistance = config.getGraph().getEdge(fromLocation, toLocation);

        route.getDistanceToLocations().add(Pair.of(toLocationDistance, toLocation));

        visited.add(toLocation);

        return toLocationDistance.getTimeWithLoadFactor()
               + toLocation.getServiceTime();
    }

    private Optional<Path> getFirstToLocationPath(
            final Car car,
            final Set<Location> visited,
            final Location fromLocation
    ) {
        final Set<Distance> fromLocationDistances = config.getGraph().edgesOf(fromLocation);

        return fromLocationDistances.stream()
                .filter(distance -> {
                    final Location toLocation = Graphs.getOppositeVertex(
                            config.getGraph(),
                            distance,
                            fromLocation
                    );

                    return !visited.contains(toLocation);
                })
                .map(distance -> {
                    final Location toLocation = Graphs.getOppositeVertex(
                            config.getGraph(),
                            distance,
                            fromLocation
                    );

                    long score = getScore(distance, toLocation, car);

                    return Pair.of(
                            new Path(fromLocation, distance, toLocation),
                            score
                    );
                })
                .sorted(Collections.reverseOrder(Comparator.comparingLong(Pair::getValue)))
                .map(Pair::getKey)
                .findFirst();
    }

    private long getScore(
            final Distance distance,
            final Location toLocation,
            final Car car
    ) {
        final int sumAfterVisit = getSumAfterVisit(car, toLocation);

        if (sumAfterVisit > car.getMaxSum()) {
            return Integer.MAX_VALUE;
        }

        final Distance toEndLocationDistance = config.getGraph().getEdge(toLocation, car.getEndLocation());

        return 0L
               // оценка - получение у клиента
               - Math.round(distance.getTimeWithLoadFactor())
               - Math.round(toLocation.getServiceTime())
               + toLocation.getSum()
               // оценка - сдача в конечную точку
               - Math.round(toEndLocationDistance.getTimeWithLoadFactor())
               - Math.round(car.getEndLocation().getServiceTime());
    }

    private int getSumAfterVisit(final Car car, final Location toLocation) {
        return car.getSum() + toLocation.getSum();
    }

}
