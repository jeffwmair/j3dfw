package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.geometry.shapes.Cube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.jwm.j3dfw.demo.voxel.VoxelObjectController.Movement.Stopped;

/**
 * Created by Jeff on 2016-09-14.
 */
public class VoxelPerson extends Cube {

    private static final Logger LOG = LogManager.getLogger(VoxelPerson.class);
    private VoxelObjectController.Movement movementState = Stopped;
    private final double MOVEMENT_SPEED = 0.1;

    /**
     * Update the state of movement
     * @param movementState
     */
    public void setMovementState(VoxelObjectController.Movement movementState) {
        if (this.movementState == movementState) {
            LOG.debug("Stopping");
            this.movementState = Stopped;
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Changing movement to:" + movementState);
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
                LOG.debug("moving left");
                increaseXTranslation(-MOVEMENT_SPEED);
                break;
            case Right:
                LOG.debug("moving right");
                increaseXTranslation(MOVEMENT_SPEED);
                break;
            case Away:
                LOG.debug("moving away");
                increaseZTranslation(-MOVEMENT_SPEED);
                break;
            case Toward:
                LOG.debug("moving toward");
                increaseZTranslation(MOVEMENT_SPEED);
                break;
            case Stopped:
                // no change
                break;
            default:
                throw new IllegalArgumentException("Unknown movementState:"+movementState);
        }

    }

}
