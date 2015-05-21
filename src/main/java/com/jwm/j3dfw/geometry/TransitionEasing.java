package com.jwm.j3dfw.geometry;

import com.jwm.j3dfw.geometry.Transition.TransitionType;

public abstract class TransitionEasing {
	private final int SPEED_REFERENCE = 5000;

	public static TransitionEasing getInstance(TransitionType type) throws Exception {
		if (type == TransitionType.LINEAR) {
		return new TransitionEasingLinear();
		}
		else if (type == TransitionType.EASE_IN_OUT_SIN) {
		return new TransitionEasingSin();	
		}
		else {
			throw new Exception("Unknown easing type:" + type.toString());
		}
	}
	public double[] getTransitionSteps(double start, double end, int speed) {
		int numberOfSteps = getNumberOfStepsFromSpeed(speed);
		double range = end - start;
//		float divisionSize = range / (numberOfSteps - 1);
		double[] scaledSteps = new double[numberOfSteps+1];
		double[] algorithmSteps = getTransitionAlgorithmSteps(numberOfSteps);
		for (int i = 0; i < algorithmSteps.length; i++) {
			scaledSteps[i] = (range * algorithmSteps[i]) + start;
		}
		return scaledSteps;
	}
	protected abstract double[] getTransitionAlgorithmSteps(int numSteps);
	protected int getNumberOfStepsFromSpeed(int speedSelection) {
		return SPEED_REFERENCE / speedSelection;
	}
}
