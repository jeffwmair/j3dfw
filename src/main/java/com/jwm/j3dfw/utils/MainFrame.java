package com.jwm.j3dfw.utils;

import java.awt.Frame;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.Scene;
import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.utils.EventListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainFrame {

	private static Logger log = LogManager.getLogger(MainFrame.class);
	
	public MainFrame(GeometryList parts, ControllerFactory controllerFactory, Camera cam) { 

		log.info("New MainFrame");
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		caps.setSampleBuffers(true);
		GLCanvas canvas = new GLCanvas(caps);

		//Geometry geo = parts.firstOrNull(Geometry.class);
		//geo.initCamera();
		//Camera cam = geo.getCamera();

		Scene scene = new Scene(parts, cam);
		EventListener listener = new EventListener(scene, parts, controllerFactory, canvas);
		canvas.addMouseMotionListener(listener);
		canvas.addMouseWheelListener(listener);
		canvas.addMouseListener(listener);
		canvas.addKeyListener(listener);
		canvas.addGLEventListener(scene);

		FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.start();

		Frame frame = new Frame();
		frame.setSize(800, 800);
		frame.add(canvas);
		frame.setVisible(true);
	}
}
