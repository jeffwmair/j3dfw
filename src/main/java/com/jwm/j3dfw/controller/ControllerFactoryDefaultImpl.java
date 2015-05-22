package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;

public class ControllerFactoryDefaultImpl implements ControllerFactory {
	public Controller getInstance(Geometry g) {
		return new Controller(g);
	}
}
