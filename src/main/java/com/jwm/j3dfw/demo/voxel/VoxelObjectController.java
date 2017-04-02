package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.controller.ControllerService;
import com.jwm.j3dfw.geometry.Geometry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * For taking input events and using them to control the voxel demo scene.
 * Created by Jeff on 2016-09-14.
 */
public class VoxelObjectController extends ControllerService {

    static Logger log = LoggerFactory.getLogger(ControllerService.class);
    enum Movement { Left, Right, Away, Toward, Stopped, NA }
    Movement movementState = Movement.Stopped;

    public void keyPress(Geometry geo, int keyCode) {

        // we just want to move the Person
        if (!(geo instanceof VoxelPerson)) {
            return;
        }

        super.keyPress(geo, keyCode);
        Movement desiredMovement = getMovementDirectionFromKeyCode(keyCode);

        if (desiredMovement == Movement.NA) {
            return;
        }

        ((VoxelPerson) geo).setMovementState(desiredMovement);


    }

    private Movement getMovementDirectionFromKeyCode(int keycode) {
        switch (keycode) {
            case KEY_DOWN_ARROW:
                return Movement.Toward;
            case KEY_UP_ARROW:
                return Movement.Away;
            case KEY_LEFT_ARROW:
                return Movement.Left;
            case KEY_RIGHT_ARROW:
                return Movement.Right;
            default:
                return Movement.NA;
        }
    }
}
