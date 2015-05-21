package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;

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
		geo.getCamera().setZoom(wheelRotation);
	}
	public void cmdMouseWheelMoved(int wheelRotation) {
		
	}
	public void shiftMouseWheelMoved(int wheeMoved) {
		
	}
}
