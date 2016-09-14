package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.controller.ControllerService;
import com.jwm.j3dfw.geometry.Geometry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * For taking input events and using them to control the voxel demo scene.
 * Created by Jeff on 2016-09-14.
 */
public class VoxelObjectController extends ControllerService {

    static Logger log = LogManager.getLogger(ControllerService.class);
    enum Movement { Left, Right, Up, Down, Stopped, NA }
    Movement movementState = Movement.Stopped;

    public void keyPress(Geometry geo, int keyCode) {
        super.keyPress(geo, keyCode);
        Movement desiredMovement = getMovementDirectionFromKeyCode(keyCode);

        if (desiredMovement == Movement.NA) {
            return;
        }

        if (movementState == desiredMovement) {
            log.debug("Stopping");
            movementState = Movement.Stopped;
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Changing movement to:" + desiredMovement);
            }
            movementState = desiredMovement;
        }
    }

    private Movement getMovementDirectionFromKeyCode(int keycode) {
        switch (keycode) {
            case KEY_DOWN_ARROW:
                return Movement.Down;
            case KEY_UP_ARROW:
                return Movement.Up;
            case KEY_LEFT_ARROW:
                return Movement.Left;
            case KEY_RIGHT_ARROW:
                return Movement.Right;
            default:
                return Movement.NA;
        }
    }
}
