package com.jwm.j3dfw.production;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Vertex;

public class Camera {
	private static Logger log = LogManager.getLogger(Camera.class);
	private final double CAM_MAX_VIEW_DISTANCE = 50000.0;
	protected Geometry targetItem;
	private double viewportWidth, viewportHeight;
	protected Vertex camera_position, camera_target;

	/* 
	 * GLU is the glut utility library which has camera helper functions 
	 */
	protected GLU glu;
	protected double zoom_distance;
	protected double h_angle;
	protected double v_pct;
	protected boolean autoRotate;
	protected boolean autoTrack;

	public Camera() {
		camera_position = new Vertex(0, 0, 10);
		camera_target = new Vertex(-10, 0, 0);
		zoom_distance = 25;
		h_angle = 90;
		v_pct = 0.75;

		this.autoRotate = false;
		this.autoTrack = false;
	}
	public void setTarget(Geometry item) {
		if (log.isDebugEnabled()) {
			log.debug("setTarget:"+item);
		}
		targetItem = item;
		camera_target = targetItem.getCenter();
	}
	public Geometry getTarget() {
		return targetItem;
	}
	public void incrementAngle(double amount) {
		this.h_angle += amount;
	}
	public void incrementVerticalAngle(double amount) {
		double pct = amount / 100.0;
		this.v_pct += pct;
	}
	public void setGlu(GLU glu) {
		this.glu = glu;
	}
	public void updateViewportDimensions(double w, double h) {
		viewportWidth = w;
		viewportHeight = h;
	}
	public Vertex getPosition() {
		return camera_position;
	}
	public void Update(GL2 gl, GLU glu) {
		// Change to projection matrix.
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		// Perspective.
		float widthHeightRatio = (float) viewportWidth / (float) viewportHeight;
		glu.gluPerspective(25, widthHeightRatio, 1, CAM_MAX_VIEW_DISTANCE);
		look();
		// Change back to model view matrix.
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	public void look() {
		double yPlaneDistance_pct = v_pct;
		double yPlaneDistance = zoom_distance * yPlaneDistance_pct;
		double verticalDistance = zoom_distance * (1 - yPlaneDistance_pct);
		if (autoTrack) {
			camera_target = targetItem.getCenter();
		}
		if (autoRotate) {
			camera_position = targetItem.getNearbyPointOnYPlane(yPlaneDistance, h_angle);
		}
		if (log.isDebugEnabled()) {
			log.debug("camera_position:" + camera_position + ", camera_target:" + camera_target + ", verticalDistance:"+verticalDistance);
		}
		glu.gluLookAt(camera_position.x, camera_position.y + verticalDistance, camera_position.z, camera_target.x, camera_target.y, camera_target.z, 0, 1, 0);
	}
	public void setZoom(int wheelRotation) {
		if (log.isDebugEnabled()) {
			log.debug("setZoom:"+wheelRotation);
		}
		zoom_distance += wheelRotation;
	}
	public void toggleAutoRotate() {
		if (!autoTrack) {
			autoRotate = false;
			return;
		}
		autoRotate = !autoRotate;
	}
	public void toggleAutoTrack() {
		autoTrack = !autoTrack;
		// gotta turn off rotation if we turn off tracking, or it gets messed up
		if (!autoTrack)
			autoRotate = false;
	}

}
