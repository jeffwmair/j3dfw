package com.jwm.j3dfw.production;

import com.jwm.j3dfw.geometry.Geometry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.glu.GLU;

public class TargetCamera extends Camera {
	private static Logger log = LogManager.getLogger(TargetCamera.class);
	private boolean autoRotate;
	private boolean autoTrack;
	private Geometry targetItem;

	public TargetCamera() {
		super();
		this.autoRotate = true;
		this.autoTrack = true;
	}
	protected void look(GLU glu) {
		double yPlaneDistance_pct = v_pct;
		double yPlaneDistance = zoom_distance * yPlaneDistance_pct;
		double verticalDistance = zoom_distance * (1 - yPlaneDistance_pct);
		if (autoTrack) {
			targetItem.getCenter(camera_target);
		}
		if (autoRotate) {
			targetItem.getNearbyPointOnYPlane(camera_position, yPlaneDistance, h_angle);
		}
		if (log.isTraceEnabled()) {
			log.trace("camera_position:" + camera_position + ", camera_target:" + camera_target + ", verticalDistance:"+verticalDistance);
		}
		glu.gluLookAt(camera_position.getX(), camera_position.getY() + verticalDistance, camera_position.getZ(), camera_target.getX(), camera_target.getY(), camera_target.getZ(), 0, 1, 0);
	}
	public void setTargetGeometry(Geometry target) {
		this.targetItem = target;
	}
	public Geometry getTargetGeometry() {
		return this.targetItem;
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
