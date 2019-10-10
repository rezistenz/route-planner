package org.rezistenz.route.planing.solution;

public class Cost {

    private int sum;
    private double time;

    public Cost() {
        // empty
    }

    public Cost(final int sum, final double time) {
        this.sum = sum;
        this.time = time;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(final int sum) {
        this.sum = sum;
    }

    public void addSum(final int sumToAdd) {
        sum += sumToAdd;
    }

    public double getTime() {
        return time;
    }

    public void setTime(final double time) {
        this.time = time;
    }

    public void addTime(final double timeToAdd) {
        time += timeToAdd;
    }

}
