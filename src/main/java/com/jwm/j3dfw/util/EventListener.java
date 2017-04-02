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
    private enum ModifierKeyPressedEnum { None, MacCommand, Shift, Alt };
    private ModifierKeyPressedEnum modifierKeyPressed = ModifierKeyPressedEnum.None;

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
            switch (modifierKeyPressed) {
                case MacCommand:
                    controllerDirectory.getInstance(g).cmdMouseWheelMoved(g, e.getWheelRotation());
                    break;
                case Alt:
                    controllerDirectory.getInstance(g).cmdMouseWheelMoved(g, e.getWheelRotation());
                    break;
                case Shift:
                    controllerDirectory.getInstance(g).shiftMouseWheelMoved(g, e.getWheelRotation());
                    break;
                default:
                    controllerDirectory.getInstance(g).mouseWheelMoved(g, e.getWheelRotation());
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_META:
                modifierKeyPressed = ModifierKeyPressedEnum.MacCommand;
                break;
            case VK_SHIFT:
                modifierKeyPressed = ModifierKeyPressedEnum.Shift;
                break;
            case VK_ALT:
                modifierKeyPressed = ModifierKeyPressedEnum.Alt;
                break;
        }
        for (Geometry g : geometryItems) {
            controllerDirectory.getInstance(g).keyPress(g, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        modifierKeyPressed = ModifierKeyPressedEnum.None;
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
