package com.jwm.j3dfw.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.awt.GLCanvas;

import com.jwm.j3dfw.controller.Controller;
import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.production.Scene;

public class EventListener implements MouseMotionListener, MouseWheelListener, MouseListener, KeyListener {

	private Scene activeScene;
	private ControllerFactory controllerFactory;
	private GLCanvas canvas;
	private GeometryList geometryItems;
	private boolean cmdKey, shiftKey;
	double lastX, lastY;

	public EventListener(Scene scene, GeometryList items, ControllerFactory controllerFactory, GLCanvas canvas) {
		this.geometryItems = items;
		this.controllerFactory = controllerFactory;
		this.canvas = canvas;
		activeScene = scene;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// activeScene.setMouseX(e.getX());
		double x = e.getX();
		double viewportWidth = activeScene.getViewportWidth();
		double xPos = x - viewportWidth / 2.0;
		double xPct = 2 * xPos / viewportWidth;
		for (Geometry g : geometryItems) {
			controllerFactory.getInstance(g).setMousePosition(xPos, xPct);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		for (Geometry g : geometryItems) {
			if (cmdKey) {
				controllerFactory.getInstance(g).mouseWheelMoved(e.getWheelRotation());
			} else if (shiftKey) {
				controllerFactory.getInstance(g).shiftMouseWheelMoved(e.getWheelRotation());
			} else {
				controllerFactory.getInstance(g).cmdMouseWheelMoved(e.getWheelRotation());
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 157:
			cmdKey = true;
			lastX = lastY = 0;
			break;
		case 16:
			shiftKey = true;
			break;
		}
		for (Geometry g : geometryItems) {
			controllerFactory.getInstance(g).keyPress(e.getKeyChar());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 157:
			cmdKey = false;
			break;
		case 16:
			shiftKey = false;
			break;
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for (Geometry g : geometryItems) {
			Controller c = controllerFactory.getInstance(g);
			switch (e.getButton()) {
			case 1:
				c.leftMouseDown();
				break;
			case 3:
				c.rightMouseDown();
				break;
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		for (Geometry g : geometryItems) {
			Controller c = controllerFactory.getInstance(g);
			switch (e.getButton()) {
			case 1:
				c.leftMouseUp();
				break;
			case 3:
				c.rightMouseUp();
				break;
			}
		}
	}

}
