package org.rezistenz.route.planing.model;

public class Location {

    private int id;
    private double serviceTime;
    private int sum;

    public Location(int id, double serviceTime, int sum) {
        this.id = id;
        this.serviceTime = serviceTime;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", serviceTime=" + serviceTime +
                ", sum=" + sum +
                '}';
    }
}
