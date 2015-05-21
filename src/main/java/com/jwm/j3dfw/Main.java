package com.jwm.j3dfw;

import java.awt.Frame;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.Scene;
import com.jwm.j3dfw.utils.EventListener;
import com.jwm.j3dfw.utils.MainFrame;

public class Main {
	
	public static void main(String[] args) {
		GeometryList parts = new GeometryList();
		Geometry plane = new Geometry();
		plane.loadRootPart("plane","plane");
		Geometry cube = new Geometry();
		cube.loadRootPart("cube","cube");
		Geometry cube2 = new Geometry();
		cube2.loadRootPart("cube","cube");
		cube2.setOverallTranslation(-2,0,0);
		parts.add(plane);
		parts.add(cube);
		parts.add(cube2);
		MainFrame frame = new MainFrame(parts);	
	}
}
