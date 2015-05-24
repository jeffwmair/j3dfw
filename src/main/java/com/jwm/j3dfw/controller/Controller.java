package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.production.Camera;

public class Controller {
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
		// 67 = c
		if (keyCode == 67) {

		}
	}
	public void setMousePosition(double xPos, double percent) {

	}
	public void mouseWheelMoved(int wheelRotation) {
		Camera cam = geo.getCamera();
		if (cam == null) {
			return;
		}
		cam.setZoom(wheelRotation);
	}
	public void cmdMouseWheelMoved(int wheelMoved) {
		Camera cam = geo.getCamera();
		if (cam == null) {
			return;
		}
		double angleChange = wheelMoved;
		cam.incrementAngle(angleChange);
	}
	public void shiftMouseWheelMoved(int wheelMoved) {
		Camera cam = geo.getCamera();
		if (cam == null) {
			return;
		}
		double angleChange = wheelMoved;
		cam.incrementVerticalAngle(angleChange);
	}
}
