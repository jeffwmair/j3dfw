package com.jwm.j3dfw.geometry;

import javax.media.opengl.GL2;

public class Translation extends Transformation {
	private double x, y, z;

	public Translation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.transType = TransformationType.translate;
	}
	public void updateByAmount(double xAmount, double yAmount, double zAmount) {
		this.x += xAmount;
		this.y += yAmount;
		this.z += zAmount;
	}
	@Override
	public void transform(GL2 gl) {
		gl.glTranslated(x, y, z);
	}
	public Vertex getTransformedVertex(Vertex v) {
		Vertex vTransformed = new Vertex(v.x + this.x, v.y + this.y, v.z + this.z);
		return vTransformed;
	}
	public void setValues(double x2, double y2, double z2) {
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}
}
