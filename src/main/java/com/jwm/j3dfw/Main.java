package com.jwm.j3dfw;

import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.controller.ControllerFactoryDefaultImpl;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.geometry.shapes.Cube;
import com.jwm.j3dfw.geometry.shapes.Plane;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.utils.MainFrame;

public class Main {
	
	public static void main(String[] args) {
		GeometryList parts = new GeometryList();
		Plane plane = new Plane();
		Cube cube = new Cube();
		Cube cube2 = new Cube();
		cube.setOverallTranslation(0,0,-3);
		cube2.setOverallTranslation(-2,0,-3);
		parts.add(plane);
		parts.add(cube);
		parts.add(cube2);
		Geometry geo = parts.firstOrNull(Geometry.class);
		geo.initCamera();
		Camera cam = geo.getCamera();
		ControllerFactory cf = new ControllerFactoryDefaultImpl();
		MainFrame frame = new MainFrame(parts, cf, cam);	
	}
}
