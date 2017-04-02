package com.jwm.j3dfw.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.glu.GLU;

public class NoTargetCamera extends TargetCamera {

    private static Logger Log = LoggerFactory.getLogger(NoTargetCamera.class);

    @Override
    protected void look(GLU glu) {
        Log.debug("NoTargetCamera.look()");
    }
}