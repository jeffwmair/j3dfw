package com.jwm.j3dfw.geometry.shapes;

import com.jwm.j3dfw.geometry.Geometry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Plane extends Geometry {
	private static Logger log = LogManager.getLogger(Plane.class);
	public Plane(String material) {
		super();
		loadRootPart("plane", material);
	}
	public Plane() {
		super();
		loadRootPart("plane", "plane");
	}
}
