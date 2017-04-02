package com.jwm.j3dfw.geometry.shapes;

import com.jwm.j3dfw.geometry.Geometry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Cube extends Geometry {
	private static Logger log = LoggerFactory.getLogger(Cube.class);
	private Cube(String material) {
		super("cube-rough", material);
	}
	public Cube() {
		super("cube-rough", "cube");
	}
}
