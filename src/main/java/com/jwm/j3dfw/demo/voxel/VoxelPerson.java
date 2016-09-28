package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.geometry.shapes.Cube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static com.jwm.j3dfw.demo.voxel.VoxelObjectController.Movement.Stopped;

/**
 * Created by Jeff on 2016-09-14.
 */
public class VoxelPerson extends Cube {

    private static final Logger LOG = LogManager.getLogger(VoxelPerson.class);
    private VoxelObjectController.Movement movementState = Stopped;
    private final double MOVEMENT_SPEED = 0.1;
    private final List<Double> upDownMovements = new ArrayList<>(Arrays.asList(0.0, 0.1, 0.2, 0.3, 0.2, 0.1, 0.0, -0.1, -0.2, -0.3, -0.2, -0.1));
    private Queue<Double> upDownMovementQueue = new ArrayBlockingQueue<Double>(12);

    public VoxelPerson() {
        for(Double val : upDownMovements) upDownMovementQueue.add(val);
    }

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
        bobUpAndDown();

    }

    private void bobUpAndDown() {
        // while walking, need to bob up and down slightly
        if (movementState != Stopped) {
            Double val = upDownMovementQueue.remove();
            increaseYTranslation(val);
            upDownMovementQueue.add(val);
        }
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
