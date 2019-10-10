package org.rezistenz.route.planing.config;

import org.jgrapht.Graph;
import org.rezistenz.route.planing.model.Car;
import org.rezistenz.route.planing.model.Distance;
import org.rezistenz.route.planing.model.Location;

import java.util.List;

public class RoutesPlanConfig {

    // Максимальное время работы (сек)
    private double maxWorkTime = 10.0 * 60;
    // Максимальное время планирования маршутов (сек)
    private double maxPlanigTime = 8.0 * 60 * 60;

    // Граф модели точек ослуживания и расстояний между ними.
    private final Graph<Location, Distance> graph;
    // Машины
    private final List<Car> cars;

    public RoutesPlanConfig(
            final double maxWorkTime,
            final double maxPlanigTime,
            final Graph<Location, Distance> graph,
            final List<Car> cars
    ) {
        this.maxWorkTime = maxWorkTime;
        this.maxPlanigTime = maxPlanigTime;
        this.graph = graph;
        this.cars = cars;
    }

    public RoutesPlanConfig(
            final Graph<Location, Distance> graph,
            final List<Car> cars
    ) {
        this.graph = graph;
        this.cars = cars;
    }

    public double getMaxWorkTime() {
        return maxWorkTime;
    }

    public double getMaxPlanigTime() {
        return maxPlanigTime;
    }

    public List<Car> getCars() {
        return cars;
    }

    public Graph<Location, Distance> getGraph() {
        return graph;
    }
}
