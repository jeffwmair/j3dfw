package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;

public interface ControllerFactory {
	public Controller getInstance(Geometry g);
}
