package com.jwm.j3dfw.geometry;

public class Transition {
	public enum TransitionType {
		LINEAR, EASE_IN_OUT_SIN
	};

	private boolean inProgress;
	private double[] steps;
	private int currentStepIndex;

	public Transition() {
		inProgress = false;
	}
	public void startTransition(double start, double end, int speedOutOf10, TransitionType type) throws Exception {
		inProgress = true;
		currentStepIndex = 0;
		steps = TransitionEasing.getInstance(type).getTransitionSteps(start, end, speedOutOf10);
	}
	public boolean isInProgress() {
		return inProgress;
	}
	public double getNext() {
		double curVal = steps[currentStepIndex];
		if (currentStepIndex == (steps.length - 1)) {
			inProgress = false;
		} else {
			currentStepIndex++;
		}
		return curVal;
	}
}
