package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.geometry.shapes.Cube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.jwm.j3dfw.demo.voxel.VoxelObjectController.Movement.Stopped;

/**
 * Created by Jeff on 2016-09-14.
 */
public class VoxelPerson extends Cube {

    static Logger log = LogManager.getLogger(VoxelPerson.class);
    private VoxelObjectController.Movement movementState = Stopped;

    /**
     * Update the state of movement
     * @param movementState
     */
    public void setMovementState(VoxelObjectController.Movement movementState) {
        if (this.movementState == movementState) {
            log.debug("Stopping");
            this.movementState = Stopped;
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Changing movement to:" + movementState);
            }
            this.movementState = movementState;
        }
    }

    @Override
    public void applyLogic() {

        movePerson();

    }

    private void movePerson() {

        switch (movementState) {
            case Left:
                log.debug("moving left");
                increaseTranslation(-0.1, 0, 0);
                break;
            case Right:
                log.debug("moving right");
                increaseTranslation(0.1, 0, 0);
                break;
            case Away:
                log.debug("moving away");
                increaseTranslation(0, 0, -0.1);
                break;
            case Toward:
                log.debug("moving toward");
                increaseTranslation(0, 0, 0.1);
                break;
            case Stopped:
                // no change
                break;
            default:
                throw new IllegalArgumentException("Unknown movementState:"+movementState);
        }

    }

}
