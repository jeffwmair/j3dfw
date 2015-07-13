package com.jwm.j3dfw.geometry.shapes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;

public class Plane extends Geometry {
	private static Logger log = LogManager.getLogger(Plane.class);
	public Plane(String material) {
		super();
		loadRootPart("surface_10m_10m", material);
	}
	public Plane() {
		super();
		loadRootPart("surface_10m_10m", "surface_10m_10m");
	}
}
