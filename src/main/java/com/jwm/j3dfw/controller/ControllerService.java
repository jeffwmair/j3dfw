package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.awt.event.KeyEvent.*;

public class ControllerService {

    private static Logger log = LoggerFactory.getLogger(ControllerService.class);
    protected final int KEY_LEFT_ARROW = 37;
    protected final int KEY_UP_ARROW = 38;
    protected final int KEY_RIGHT_ARROW = 39;
    protected final int KEY_DOWN_ARROW = 40;
    private static final int MOUSE_SCROLL_SPEED = 2;

    public void leftMouseDown(Geometry geo) {

    }

    public void rightMouseDown(Geometry geo) {

    }

    public void leftMouseUp(Geometry geo) {

    }

    public void rightMouseUp(Geometry geo) {

    }

    public void keyPress(Geometry geo, int keyCode) {

        // 67 = c
        if (keyCode == 67) {

        }
    }

    public void setMousePosition(Geometry geo, double xPos, double percent) {

    }

    public void mouseWheelMoved(Geometry geo, int wheelMoved, int pressedKeyCode) {
        int movementAmount = wheelMoved * MOUSE_SCROLL_SPEED;
        switch (pressedKeyCode) {
            case VK_META:
                geo.getCamera().setZoom(movementAmount);
                break;
            case VK_ALT:
                geo.getCamera().setZoom(movementAmount);
                break;
            case VK_SHIFT:
                geo.getCamera().incrementVerticalAngle(movementAmount);
                break;
            default:
                geo.getCamera().incrementAngle(movementAmount);
                break;
        }
    }
}
