package com.jwm.j3dfw.demo.buildings;

import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.util.MainFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DemoBuildingsMain {
	
	private static Logger log = LogManager.getLogger(DemoBuildingsMain.class);
	public static void main(String[] args) {

		ControllerDirectory cd = new ControllerDirectoryDefaultImpl();
		GeometryFactory geometryFactory = new DemoGeometryFactoryImpl();

		int targetFps = 60;
		int frameWidth = 800;
		int frameHeight = 800;
		MainFrame.startMainFrame(geometryFactory, cd, targetFps, frameWidth, frameHeight);
	}

}
