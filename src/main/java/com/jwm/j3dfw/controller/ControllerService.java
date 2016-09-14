package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.production.Camera;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ControllerService {

	private static Logger log = LogManager.getLogger(ControllerService.class);

	public void leftMouseDown(Geometry geo) {

	}
	public void rightMouseDown(Geometry geo) {

	}
	public void leftMouseUp(Geometry geo) {

	}
	public void rightMouseUp(Geometry geo) {

	}
	public void keyPress(Geometry geo, int keyCode) {

		if (log.isDebugEnabled()) {
			log.debug("keyPress:" + keyCode);
		}

		// 67 = c
		if (keyCode == 67) {

		}
	}
	public void setMousePosition(Geometry geo, double xPos, double percent) {

	}
	public void mouseWheelMoved(Geometry geo, int wheelRotation) {

		if (log.isDebugEnabled()) {
			log.debug("mouseWheelMoved:" + wheelRotation);
		}

		Camera cam = geo.getCamera();
		if (cam == null) {
			if (log.isDebugEnabled()) {
				log.debug("mouseWheelMoved. Camera is null on geo:"+geo);
			}
			return;
		}
		cam.setZoom(wheelRotation);
	}
	public void cmdMouseWheelMoved(Geometry geo, int wheelMoved) {

		if (log.isDebugEnabled()) {
			log.debug("cmdMouseWheelMoved:" + wheelMoved);
		}

		Camera cam = geo.getCamera();
		if (cam == null) {
			if (log.isDebugEnabled()) {
				log.debug("mouseWheelMoved. Camera is null on geo:"+geo);
			}
			return;
		}
		double angleChange = wheelMoved;
		cam.incrementAngle(angleChange);
	}
	public void shiftMouseWheelMoved(Geometry geo, int wheelMoved) {
		if (log.isDebugEnabled()) {
			log.debug("shiftMouseWheelMoved:" + wheelMoved);
		}
		Camera cam = geo.getCamera();
		if (cam == null) {
			if (log.isDebugEnabled()) {
				log.debug("mouseWheelMoved. Camera is null on geo:"+geo);
			}
			return;
		}
		double angleChange = wheelMoved;
		cam.incrementVerticalAngle(angleChange);
	}
}
