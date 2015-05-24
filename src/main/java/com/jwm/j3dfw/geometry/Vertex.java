package com.jwm.j3dfw.geometry;

public class Vertex {
	public double x, y, z;

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public double getOverheadAngleToOtherVertex(Vertex other) {
		double x = other.x - this.x;
		double z = other.z - this.z;
		double theta = Math.asin(x / z);
		double theta_degrees = Math.toDegrees(theta);
		return theta_degrees;
	}

	@Override
	public String toString() {
		return "Vertex x:"+x+", y:" + y + ", z:" + z;
	}
}
