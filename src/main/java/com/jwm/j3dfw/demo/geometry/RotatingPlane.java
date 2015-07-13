package com.jwm.j3dfw.demo.geometry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jwm.j3dfw.geometry.Rotation.RotationDirection;
import com.jwm.j3dfw.geometry.shapes.Plane;

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

		// just rotate around in a circle
		currentRotationAmt += 0.1;
		setRotation(currentRotationAmt, RotationDirection.overhead);
	}
}
