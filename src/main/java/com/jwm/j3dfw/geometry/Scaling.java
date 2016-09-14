package com.jwm.j3dfw.geometry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GL2;

public class Scaling extends Transformation {
	private static Logger log = LogManager.getLogger(Transformation.class);
	private double x, y, z;

	public Scaling(double x, double y, double z) {
		if (log.isDebugEnabled()) {
			log.debug("New "+this.toString());
		}
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void update(double amount) {
		this.x += amount;
		this.y += amount;
		this.z += amount;
	}
	public void set(double amount) {
		this.x = amount;
		this.y = amount;
		this.z = amount;
	}
	@Override
	public void transform(GL2 gl) {
		gl.glScaled(x, y, z);
	}
	public void set(double x2, double y2, double z2) {
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}
}
