package com.jwm.j3dfw.production;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.glu.GLU;

public class DefaultCamera extends Camera {
	private static Logger log = LogManager.getLogger(DefaultCamera.class);

	public DefaultCamera() {
		super();
	}
	protected void look(GLU glu) {
		glu.gluLookAt(camera_position.getX(), camera_position.getY(), camera_position.getZ(), camera_target.getX(), camera_target.getY(), camera_target.getZ(), 0, 1, 0);
	}
}
