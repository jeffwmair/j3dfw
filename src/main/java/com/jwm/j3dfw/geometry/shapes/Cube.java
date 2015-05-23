package com.jwm.j3dfw.geometry.shapes;

import com.jwm.j3dfw.geometry.Geometry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cube extends Geometry {
	private static Logger log = LogManager.getLogger(Cube.class);
	public Cube(String material) {
		super();
		loadRootPart("cube", material);
	}
	public Cube() {
		super();
		loadRootPart("cube", "cube");
	}
}
