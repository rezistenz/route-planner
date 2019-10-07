package org.rezistenz.route.planing.model;

public class Car {
	
	private int maxSum;
	private int sum;
	
	private Location startLocation;
	private Location endLocation;

	public int getMaxSum() {
		return maxSum;
	}

	public void setMaxSum(int maxSum) {
		this.maxSum = maxSum;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}
}
