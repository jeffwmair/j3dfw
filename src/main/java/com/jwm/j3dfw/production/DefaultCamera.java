package com.jwm.j3dfw.production;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Vertex;

public class DefaultCamera extends Camera {
	private static Logger log = LogManager.getLogger(DefaultCamera.class);

	public DefaultCamera() {
		super();
	}
	protected void look(GLU glu) {
		glu.gluLookAt(camera_position.x, camera_position.y, camera_position.z, camera_target.x, camera_target.y, camera_target.z, 0, 1, 0);
	}
}
