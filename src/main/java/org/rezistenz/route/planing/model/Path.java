package org.rezistenz.route.planing.model;

public class Path {

    private Location from;
    private Distance distance;
    private Location to;

    public Path(final Location from, final Distance distance, final Location to) {
        this.from = from;
        this.distance = distance;
        this.to = to;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(final Location from) {
        this.from = from;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(final Distance distance) {
        this.distance = distance;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(final Location to) {
        this.to = to;
    }
}
