package com.jwm.j3dfw.util;

import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.controller.ControllerService;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.production.Scene;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.awt.event.*;
import java.util.List;

import static java.awt.event.KeyEvent.*;

class EventListener implements MouseMotionListener, MouseWheelListener, MouseListener, KeyListener {

    private static Logger log = LoggerFactory.getLogger(EventListener.class);
    private Scene activeScene;
    private ControllerDirectory controllerDirectory;
    private List<Geometry> geometryItems;
    private int pressedKey;

    EventListener(Scene scene, List<Geometry> items, ControllerDirectory controllerDirectory) {
        this.geometryItems = items;
        this.controllerDirectory = controllerDirectory;
        activeScene = scene;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double x = e.getX();
        double viewportWidth = activeScene.getViewportWidth();
        double xPos = x - viewportWidth / 2.0;
        double xPct = 2 * xPos / viewportWidth;
        for (Geometry g : geometryItems) {
            controllerDirectory.getInstance(g).setMousePosition(g, xPos, xPct);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        for (Geometry g : geometryItems) {
            controllerDirectory.getInstance(g).mouseWheelMoved(g, e.getWheelRotation(), pressedKey);

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKey = e.getKeyCode();
        for (Geometry g : geometryItems) {
            controllerDirectory.getInstance(g).keyPress(g, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKey = 0;
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
            ControllerService c = controllerDirectory.getInstance(g);
            switch (e.getButton()) {
                case 1:
                    c.leftMouseDown(g);
                    break;
                case 3:
                    c.rightMouseDown(g);
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Geometry g : geometryItems) {
            ControllerService c = controllerDirectory.getInstance(g);
            switch (e.getButton()) {
                case 1:
                    c.leftMouseUp(g);
                    break;
                case 3:
                    c.rightMouseUp(g);
                    break;
            }
        }
    }

}
