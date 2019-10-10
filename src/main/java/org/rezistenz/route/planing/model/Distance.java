package org.rezistenz.route.planing.model;

/** Путь */
public class Distance {

    /** Длина */
    private double length;
    /** Время */
    private double time;
    /** Загрузка */
    private double loadFactor;

    public Distance(double length, double time, double loadFactor) {
        this.length = length;
        this.time = time;
        this.loadFactor = loadFactor;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }

    public double getTimeWithLoadFactor() {
        return time * loadFactor;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "length=" + length +
                ", time=" + time +
                ", loadFactor=" + loadFactor +
                '}';
    }
}
