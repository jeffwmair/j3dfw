package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;

public class ControllerFactory {
	public static Controller getInstance(Geometry g) {
		return new Controller(g);
	}
}
