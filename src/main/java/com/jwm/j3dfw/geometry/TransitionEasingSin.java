package com.jwm.j3dfw.geometry;

public class TransitionEasingSin extends TransitionEasing {
	@Override
	protected double[] getTransitionAlgorithmSteps(int numSteps) {
		double[] steps = new double[numSteps + 1];
		TransitionEasingLinear linearStepMaker = new TransitionEasingLinear();
		double[] linearSteps = linearStepMaker.getTransitionAlgorithmSteps(numSteps);
		for (int i = 0; i <= numSteps; i++) {
			double time = linearSteps[i];
			double degrees90Range = to90DegreeRange(time);
			double radians = Math.toRadians(degrees90Range);
			double sinYVal = Math.sin(radians);
			// sin y value is from -1 to 1; needs to be converted to 0 to 1
			// so, add 1, then mult by 0.5
			double shiftedSin = 1 + sinYVal;
			double scaledShiftedSin = shiftedSin * 0.5;

			scaledShiftedSin = scaledShiftedSin*scaledShiftedSin;
			steps[i] = (float) scaledShiftedSin;
		}
		return steps;
	}
	private double to90DegreeRange(double val0to1) {
		// convert from the 0 to 1 range to -90 to 90 degrees
		// first shift to -0.5 to 0.5
		double localVal = val0to1 - 0.5f;
		// then mult by 180 for degrees; eg, 0.5 * 180 = 90 that we want
		localVal *= 180;
		return localVal;
	}
}
