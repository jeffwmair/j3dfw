package com.jwm.j3dfw.production;

import com.jwm.j3dfw.geometry.Vertex;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public abstract class Camera {
	private static Logger log = LogManager.getLogger(Camera.class);
	private double viewportWidth, viewportHeight;
	Vertex camera_position;
	Vertex camera_target;

	/* 
	 * GLU is the glut utility library which has camera helper functions 
	 */
	double zoom_distance;
	double h_angle;
	double v_pct;

	/**
	 * Template method for specific camear implementations to implement
	 */
	protected abstract void look(GLU glu);

	Camera() {
		log.info("Created new camera");
		camera_position = new Vertex(0, 0, 10);
		camera_target = new Vertex(0, 0, 0);
		zoom_distance = 25;
		h_angle = 90;
		v_pct = 0.75;
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
		double CAM_MAX_VIEW_DISTANCE = 50000.0;
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
