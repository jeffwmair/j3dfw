package com.jwm.j3dfw.util;

import com.jogamp.opengl.util.FPSAnimator;
import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.Scene;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.util.List;

/**
 * Main GUI frame.
 */
public class MainFrame {

	private static Logger log = LogManager.getLogger(MainFrame.class);
	private static MainFrame instance;

	/**
	 * Get a singleton instance
	 * @param geometryFactory
	 * @param controllerDirectory
     * @return
     */
	public static synchronized void startMainFrame(GeometryFactory geometryFactory, ControllerDirectory controllerDirectory, int targetFps, int frameWidth, int frameHeight) {
		if (instance != null) {
			return;
		}

		instance = new MainFrame(geometryFactory, controllerDirectory, targetFps, frameWidth, frameHeight);
	}

	private MainFrame(GeometryFactory geometryFactory, ControllerDirectory controllerDirectory, int targetFps, int frameWidth, int frameHeight) {
		log.info("New MainFrame");
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		caps.setSampleBuffers(true);
		GLCanvas canvas = new GLCanvas(caps);

		List<Geometry> parts = geometryFactory.buildGeometryItems();
		Camera cam = geometryFactory.getMainCamera();
		Scene scene = new Scene(parts, cam);
		EventListener listener = new EventListener(scene, parts, controllerDirectory);
		canvas.addMouseMotionListener(listener);
		canvas.addMouseWheelListener(listener);
		canvas.addMouseListener(listener);
		canvas.addKeyListener(listener);
		canvas.addGLEventListener(scene);

		FPSAnimator animator = new FPSAnimator(canvas, targetFps);
		animator.start();

		Frame frame = new Frame();
		frame.setSize(frameWidth, frameHeight);
		frame.add(canvas);
		frame.setVisible(true);
	}

}
