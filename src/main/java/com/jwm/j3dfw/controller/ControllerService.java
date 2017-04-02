package com.jwm.j3dfw.controller;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.production.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerService {

    private static Logger log = LoggerFactory.getLogger(ControllerService.class);
    protected final int KEY_LEFT_ARROW = 37;
    protected final int KEY_UP_ARROW = 38;
    protected final int KEY_RIGHT_ARROW = 39;
    protected final int KEY_DOWN_ARROW = 40;

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

    public void mouseWheelMoved(Geometry geo, int wheelMoved) {
        geo.getCamera().incrementAngle(wheelMoved);
    }

    public void cmdMouseWheelMoved(Geometry geo, int wheelMoved) {
        geo.getCamera().setZoom(wheelMoved);
    }

    public void shiftMouseWheelMoved(Geometry geo, int wheelMoved) {
        geo.getCamera().incrementVerticalAngle(wheelMoved);
    }
}
