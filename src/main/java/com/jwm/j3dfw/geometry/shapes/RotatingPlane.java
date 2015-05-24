package com.jwm.j3dfw.geometry.shapes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Rotation.RotationDirection;

public class RotatingPlane extends Plane {
	private static Logger log = LogManager.getLogger(RotatingPlane.class);
	private double currentRotationAmt;
	public RotatingPlane() {
		super();
	}

	@Override
	protected void applyLogic() {
		currentRotationAmt += 1.0;
		setRotation(currentRotationAmt, RotationDirection.overhead);
	}
}
