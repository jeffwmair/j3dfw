package com.jwm.j3dfw.geometry;

public class TransitionEasingLinear extends TransitionEasing {
	@Override
	protected double[] getTransitionAlgorithmSteps(int numSteps) {
		double[] steps = new double[numSteps + 1];
		for (int i = 0; i <= numSteps; i++) {
			float val = (float) i;
			steps[i] = val / numSteps;
		}
		return steps;
	}
}
