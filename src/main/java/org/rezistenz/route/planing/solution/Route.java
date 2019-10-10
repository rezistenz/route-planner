package org.rezistenz.route.planing.solution;

import org.apache.commons.lang3.tuple.Pair;
import org.rezistenz.route.planing.model.Car;
import org.rezistenz.route.planing.model.Distance;
import org.rezistenz.route.planing.model.Location;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private Car car;

    private List<Pair<Distance, Location>> distanceToLocations = new ArrayList<>();

    public Car getCar() {
        return car;
    }

    public void setCar(final Car car) {
        this.car = car;
    }

    public List<Pair<Distance, Location>> getDistanceToLocations() {
        return distanceToLocations;
    }

    public Cost buildCost() {
        final Cost cost = new Cost(0, 0.0);

        distanceToLocations.forEach(item -> {
            final Distance toLocationDistance = item.getKey();
            final Location toLocation = item.getValue();

            if (car.getStartLocation().equals(toLocation)) {
                return;
            }

            if (!car.getEndLocation().equals(toLocation)) {
                cost.addSum(toLocation.getSum());
            }

            cost.addTime(toLocationDistance.getTimeWithLoadFactor());
            cost.addTime(toLocation.getServiceTime());
        });

        return cost;
    }
}
