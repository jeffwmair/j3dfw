package com.jwm.j3dfw.geometry.shapes;

import com.jwm.j3dfw.geometry.Geometry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Plane extends Geometry {
	private static Logger log = LogManager.getLogger(Plane.class);
	private Plane(String material) {
		super("surface_10m_10m", material);
	}
	public Plane() {
		super("surface_10m_10m", "surface_10m_10m");
	}
}
