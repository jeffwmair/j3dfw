package com.jwm.j3dfw.production;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Vertex;

public abstract class Camera {
	private static Logger log = LogManager.getLogger(Camera.class);
	private final double CAM_MAX_VIEW_DISTANCE = 50000.0;
	private double viewportWidth, viewportHeight;
	protected Vertex camera_position, camera_target;

	/* 
	 * GLU is the glut utility library which has camera helper functions 
	 */
	protected double zoom_distance;
	protected double h_angle;
	protected double v_pct;
	private GLU glu;

	/**
	 * Template method for specific camear implementations to implement
	 */
	protected abstract void look(GLU glu);

	public Camera() {
		camera_position = new Vertex(0, 0, 10);
		camera_target = new Vertex(0, 0, 0);
		zoom_distance = 25;
		h_angle = 90;
		v_pct = 0.75;
	}
	public void setGlu(GLU glu) {
		this.glu = glu;
	}

	/**
	 * Used during window resize
	 */
	public void updateViewportDimensions(double w, double h) {
		viewportWidth = w;
		viewportHeight = h;
	}

	/**
	 * Gets the current camera position
	 */
	public Vertex getPosition() {
		return camera_position;
	}

	/**
	 * Update method that should be called in the main GL event loop
	 */
	public void update(GL2 gl, GLU glu) {
		// Change to projection matrix.
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		// Perspective.
		float widthHeightRatio = (float) viewportWidth / (float) viewportHeight;
		glu.gluPerspective(25, widthHeightRatio, 1, CAM_MAX_VIEW_DISTANCE);
		look(glu);
		// Change back to model view matrix.
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void setTarget(Vertex target) {
		if (log.isDebugEnabled()) {
			log.debug("setTarget:"+target);
		}
		camera_target = target;
	}
	public Vertex getTarget() {
		return camera_target;
	}

	public void incrementAngle(double amount) {
		this.h_angle += amount;
	}

	public void incrementVerticalAngle(double amount) {
		double pct = amount / 100.0;
		this.v_pct += pct;
	}
	public void setZoom(int wheelRotation) {
		if (log.isDebugEnabled()) {
			log.debug("setZoom:"+wheelRotation);
		}
		zoom_distance += wheelRotation;
	}
}
