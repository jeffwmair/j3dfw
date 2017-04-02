package com.jwm.j3dfw.geometry.shapes;

import com.jwm.j3dfw.geometry.Geometry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Plane extends Geometry {
	private static Logger log = LoggerFactory.getLogger(Plane.class);
	private Plane(String material) {
		super("surface_10m_10m", material);
	}
	public Plane() {
		super("surface_10m_10m", "surface_10m_10m");
	}
}
