package com.jwm.j3dfw.geometry;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Transition {
    private static Logger log = LoggerFactory.getLogger(Transition.class);
    public enum TransitionType {
        LINEAR, EASE_IN_OUT_SIN
    }

    private boolean inProgress;
    private double[] steps;
    private int currentStepIndex;

    public Transition() {
            log.debug("New transition");
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
