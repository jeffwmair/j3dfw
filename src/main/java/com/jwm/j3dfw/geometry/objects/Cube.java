package com.jwm.j3dfw.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;

public class Cube extends Geometry {
	public Cube(String material) {
		super();
		loadRootPart("cube", material);
	}
	public Cube() {
		super();
		loadRootPart("cube", "cube");
	}
}
