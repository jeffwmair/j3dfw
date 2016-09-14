package com.jwm.j3dfw.geometry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GL2;

public class Translation extends Transformation {
	private static Logger log = LogManager.getLogger(Translation.class);
	private double x, y, z;

	public Translation(double x, double y, double z) {
		if (log.isDebugEnabled()) {
			log.debug("New Translation " + x + ", " + y + ", " + z);
		}
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
	public void transformVertex(Vertex v) {
		v.setX(v.getX()+x);
		v.setY(v.getY()+y);
		v.setZ(v.getZ()+z);
	}
	public void setValues(double x2, double y2, double z2) {
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}
}
