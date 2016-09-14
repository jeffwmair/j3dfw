package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;

public interface ControllerDirectory {
	ControllerService getInstance(Geometry g);
}
