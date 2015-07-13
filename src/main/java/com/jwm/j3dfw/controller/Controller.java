package com.jwm.j3dfw.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.production.Camera;

public class Controller {

	private static Logger log = LogManager.getLogger(Controller.class);
	private Geometry geo;

	public Controller(Geometry g) {
		geo = g;
	}
	public void leftMouseDown() {

	}
	public void rightMouseDown() {

	}
	public void leftMouseUp() {

	}
	public void rightMouseUp() {

	}
	public void keyPress(int keyCode) {

		if (log.isDebugEnabled()) {
			log.debug("keyPress:" + keyCode);
		}

		// 67 = c
		if (keyCode == 67) {

		}
	}
	public void setMousePosition(double xPos, double percent) {

	}
	public void mouseWheelMoved(int wheelRotation) {

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
	public void cmdMouseWheelMoved(int wheelMoved) {

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
	public void shiftMouseWheelMoved(int wheelMoved) {
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
