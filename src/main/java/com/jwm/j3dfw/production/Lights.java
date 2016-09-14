package com.jwm.j3dfw.production;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import java.nio.FloatBuffer;

class Lights {
	private static Logger log = LogManager.getLogger(Lights.class);
	private static float spotlightAngle;
	private static FloatBuffer model_ambient_buff;
	private static FloatBuffer light_diffuse0_buff;
	private static FloatBuffer light_specular0_buff;
	private static FloatBuffer light_position0_buff;
	private static FloatBuffer light_spot0_dir_buff;
	private static boolean useSpotlight;
	static {
		/*
		 * From openGl Superbible 4th: light position contains x, y, and z that
		 * specifies either the lights actual position in the scene or the direction
		 * from which the light is coming.
		 * 
		 * The last value indicates that the light is actually present at this
		 * location. By default, the light radiates equally in all directions
		 * from this location, but you can change this default to make it a
		 * spotlight.
		 * 
		 * Setting to 0 creates directional light source: all rays strike at the
		 * same angle. This is like the sun which is (essentially) infinitely
		 * far away.
		 * 
		 * Setting to 1 puts the light at the location of the xyz points.
		 */
		
		/**
		 * X is the distance along XZ plane (the ground)
		 */
		float lightX = 20f;
		/**
		 * Z is the "depth" or distance "down" the XZ plane (the ground)
		 */
		float lightZ = -20f;
		/**
		 * Y is the height off the ground
		 */
		float lightY = 20f;

		/**
		 * Spotlight vs general unfocused (better-lit) light
		 */
		useSpotlight = false;

		float modelLight = 1f;
		float diffLevel = 1f;
		float specLevel = 0.3f;
		
		spotlightAngle = 15f;
		float spotlightParam = 1;
		if (!useSpotlight)
			spotlightParam = 0;
		float light_position0[] = { lightX, lightY, lightZ, spotlightParam };
		float light_direction0[] = { 0, -1, 0 };
		float light_diffuse0[] = { diffLevel, diffLevel, diffLevel, 1.0f };
		float light_specular0[] = { specLevel, specLevel, specLevel, 1.0f };
		float model_ambient[] = { modelLight, modelLight, modelLight, 1.0f };
		model_ambient_buff = FloatBuffer.wrap(model_ambient);
		light_diffuse0_buff = FloatBuffer.wrap(light_diffuse0);
		light_specular0_buff = FloatBuffer.wrap(light_specular0);
		light_position0_buff = FloatBuffer.wrap(light_position0);
		light_spot0_dir_buff = FloatBuffer.wrap(light_direction0);
	}

	public static void setupLighting(GL2 gl) {
		// Global settings.
		if (log.isTraceEnabled()) {
			log.trace("setupLighting [spotlight:"+useSpotlight+"]");
		}
		gl.glEnable(GL2.GL_MULTISAMPLE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
		gl.glEnable(GL2.GL_POLYGON_SMOOTH_HINT);
		gl.glHint(GL2.GL_MULTISAMPLE_FILTER_HINT_NV, GL2.GL_NICEST);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glLightModelfv(GL2ES1.GL_LIGHT_MODEL_AMBIENT, model_ambient_buff);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, light_diffuse0_buff);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, light_specular0_buff);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, light_position0_buff);
		if (useSpotlight) {
			gl.glLightfv(GL2.GL_LIGHT0, GLLightingFunc.GL_SPOT_DIRECTION, light_spot0_dir_buff);
			gl.glLightf(GL2.GL_LIGHT0, GLLightingFunc.GL_SPOT_CUTOFF, spotlightAngle);
		}
	}
}
