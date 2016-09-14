package com.jwm.j3dfw.geometry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GL2;

public class Rotation extends Transformation {
	private static Logger log = LogManager.getLogger(Rotation.class);
	private double angle, x, y, z;

	private static Vertex vertexStraightUp;

	private static Vertex getStraightUpVector() {
		if (vertexStraightUp == null) {
			log.debug("getStraightUpVector");
			vertexStraightUp = new Vertex(0, 1, 0);
		}
		return vertexStraightUp;
	}

	private static Vertex vectorX;

	private static Vertex getXVector() {
		if (vectorX == null) {
			log.debug("getXVector");
			vectorX = new Vertex(1, 0, 0);
		}
		return vectorX;
	}

	private static Vertex vectorZ;

	private static Vertex getZVector() {
		if (vectorZ == null) {
			log.debug("getZVector");
			vectorZ = new Vertex(0, 0, 1);
		}
		return vectorZ;
	}

	private Vertex getVertexForRotationDir(RotationDirection dir) {
		switch (dir) {
		case overhead:
			return getStraightUpVector();
		case endToEnd:
			return getXVector();
		case leftAndRight:
			return getZVector();
		default:
			return getStraightUpVector();
		}
	}

	public static Rotation getOverheadRotation() {
		// for overhead rotation, we need a "straight up" vector
		Vertex v = getStraightUpVector();
		return new Rotation(0, v.x, v.y, v.z);
	}
	public static Rotation getRotationOnXVector() {
		Vertex v = getXVector();
		return new Rotation(0, v.x, v.y, v.z);
	}
	public static Rotation getRotationOnZVector() {
		Vertex v = getZVector();
		return new Rotation(0, v.x, v.y, v.z);
	}

	public enum RotationDirection {
		overhead, leftAndRight, endToEnd
	}

	public Rotation(double angle, RotationDirection dir) {
		if (log.isDebugEnabled()) {
			log.debug("new " + this.toString());
		}
		Vertex v = getVertexForRotationDir(dir);
		setValues(angle, v.x, v.y, v.z);
		this.transType = TransformationType.rotate;
	}
	private Rotation(double angle, double vecX, double vecY, double vecZ) {
		if (log.isDebugEnabled()) {
			log.debug("new " + this.toString());
		}
		setValues(angle, vecX, vecY, vecZ);
		this.transType = TransformationType.rotate;
	}
	@Override
	public void transform(GL2 gl) {
		gl.glRotated(angle, x, y, z);
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public void setValues(double angle, double vecX, double vecY, double vecZ) {
		this.angle = angle;
		this.x = vecX;
		this.y = vecY;
		this.z = vecZ;
	}
	public void increaseAngle(double amt) {
		this.angle += amt;
	}
}
