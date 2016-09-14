package com.jwm.j3dfw.demo.buildings;

import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.demo.ControllerDirectoryDefaultImpl;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.util.MainFrame;

public class DemoBuildingsMain {
	
	public static void main(String[] args) {
		ControllerDirectory cd = new ControllerDirectoryDefaultImpl();
		GeometryFactory geometryFactory = new DemoGeometryFactoryImpl();
		MainFrame.startMainFrameWithDefaults(geometryFactory, cd);
	}

}
