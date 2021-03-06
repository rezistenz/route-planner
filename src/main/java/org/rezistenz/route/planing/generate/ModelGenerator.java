package org.rezistenz.route.planing.generate;

import org.jgrapht.Graph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.rezistenz.route.planing.model.Distance;
import org.rezistenz.route.planing.model.Location;

import java.util.Random;
import java.util.Set;

public class ModelGenerator {

    private int locCount = 100;

    private int locMaxSum = 100; // 1000 rub
    private int locMaxServiceTime = 30; // min

    private int distMaxLength = 30; // km
    private int distMinSpeed = 20; // km/h
    private int distMaxSpeed = 80; // km/h
    private int distMaxLoadFactor = 10;  // real speed = defult speed / load factor

    public ModelGenerator setLocCount(final int locCount) {
        this.locCount = locCount;
        return this;
    }

    public ModelGenerator setLocMaxSum(final int locMaxSum) {
        this.locMaxSum = locMaxSum;
        return this;
    }

    public ModelGenerator setLocMaxServiceTime(final int locMaxServiceTime) {
        this.locMaxServiceTime = locMaxServiceTime;
        return this;
    }

    public ModelGenerator setDistMaxLength(final int distMaxLength) {
        this.distMaxLength = distMaxLength;
        return this;
    }

    public ModelGenerator setDistMinSpeed(final int distMinSpeed) {
        this.distMinSpeed = distMinSpeed;
        return this;
    }

    public ModelGenerator setDistMaxSpeed(final int distMaxSpeed) {
        this.distMaxSpeed = distMaxSpeed;
        return this;
    }

    public ModelGenerator setDistMaxLoadFactor(final int distMaxLoadFactor) {
        this.distMaxLoadFactor = distMaxLoadFactor;
        return this;
    }

    public Graph<Location, Distance> generate() {
        final Graph<Location, Distance> graph = GraphTypeBuilder
                .undirected()
                .vertexClass(Location.class)
                .edgeClass(Distance.class)
                .buildGraph();

        final Random random = new Random();

        generateLocations(graph, random);

        final Set<Location> locations = graph.vertexSet();

        for (final Location locationFrom: locations) {
            for (final Location locationTo: locations) {
                if (locationFrom.getId() == locationTo.getId()) {
                    continue;
                }
                final Distance distance = generateDistance(random);

                graph.addEdge(
                        locationFrom,
                        locationTo,
                        distance
                );
            }
        }

        return graph;
    }

    private void generateLocations(final Graph<Location, Distance> graph, final Random random) {
        for (int index = 0; index < locCount; index++) {
            final Location location = new Location(
                    index,
                    (1 + random.nextInt(locMaxServiceTime)) * 60,
                    (1 + random.nextInt(locMaxSum)) * 1000
            );
            graph.addVertex(location);
        }
    }

    private Distance generateDistance(final Random random) {
        // S=V*T
        final double length = 1 + random.nextInt(distMaxLength); // km
        // V=S/T
        final double speed = (distMinSpeed + random.nextInt(distMaxSpeed - distMinSpeed)) ; // km/h
        // T=S/V
        final double time = (length / speed) * 60 * 60; // sec

        final double loadFactor = 1 + random.nextInt(distMaxLoadFactor);

        return new Distance(
                length,
                time,
                loadFactor
        );
    }

}
