package com.jwm.j3dfw.geometry.shapes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Rotation;
import com.jwm.j3dfw.geometry.Rotation.RotationDirection;

public class RotatingPlane extends Plane {
	private static Logger log = LogManager.getLogger(RotatingPlane.class);
	private double currentRotationAmt;
	public RotatingPlane() {
		super();
		/* TODO: sort out this magic number.  It is
		 * used to make the axis of rotation of the
		 * plane be its centre.
		 */
		setOffsetFromOrigin(0.0, 0.0, -6.0);
	}

	@Override
	protected void applyLogic() {
		currentRotationAmt += 0.25;

		/*
		Rotation r = getPostTranslateRotation();
		// 0 1 0 should be a "straight up" vector for overhead rotation
		r.setValues(currentRotationAmt, 0, 1, 0);
		*/

		setRotation(currentRotationAmt, RotationDirection.overhead);
	}
}
