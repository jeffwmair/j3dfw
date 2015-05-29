package com.jwm.j3dfw.production;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Vertex;

public class TargetCamera extends Camera {
	private static Logger log = LogManager.getLogger(TargetCamera.class);
	protected Geometry targetItem;
	protected boolean autoRotate;
	protected boolean autoTrack;

	public TargetCamera() {
		super();
		this.autoRotate = true;
		this.autoTrack = true;
	}
	protected void look() {
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

}
