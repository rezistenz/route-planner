package org.rezistenz.route.planing.app;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.junit.Test;
import org.rezistenz.route.planing.config.RoutesPlanConfig;
import org.rezistenz.route.planing.generate.ModelGenerator;
import org.rezistenz.route.planing.model.Car;
import org.rezistenz.route.planing.model.Distance;
import org.rezistenz.route.planing.model.Location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RoutesPlanAppTest {

    @Test
    public void testRoutesPlanExec() {
        final Graph<Location, Distance> graph = new ModelGenerator()
                .setLocCount(200)
                .setLocMaxServiceTime(15)
                .setLocMaxSum(100)
                .setDistMaxLength(5)
                .setDistMaxLoadFactor(5)
                .generate();

        final Location startLocation = graph.vertexSet().stream()
                .findFirst().orElseThrow(() -> new RuntimeException("startLocation not found"));

        final Distance toEndLocationWithMinDistance = graph.edgesOf(startLocation).stream()
                .min(Comparator.comparingDouble(Distance::getLength))
                .orElseThrow(() -> new RuntimeException("endLocation edge not found"));

        final Location endLocation = Graphs.getOppositeVertex(
                graph,
                toEndLocationWithMinDistance,
                startLocation
        );

        final List<Car> cars = new ArrayList<>();

        final Car car = new Car();

        car.setMaxSum(1_000_000);
        car.setStartLocation(startLocation);
        car.setEndLocation(endLocation);

        cars.add(car);

        final RoutesPlanConfig config = new RoutesPlanConfig(
                graph,
                cars
        );

        final RoutesPlanApp app = new RoutesPlanApp(config);

        app.routesPlanExec();
    }

}
