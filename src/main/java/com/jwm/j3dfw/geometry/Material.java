package com.jwm.j3dfw.geometry;

import com.jogamp.common.nio.Buffers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.nio.FloatBuffer;

/**
 * A material contains colour components; determines how a Geometry looks
 * 
 * @author Jeff
 *
 */
class Material {

	private static Logger log = LogManager.getLogger(Material.class);
	// todo: replace with getters
	public FloatBuffer ambient;
	public FloatBuffer diffuse;
	public FloatBuffer specular;
	public FloatBuffer shinyness;

	public Material(float[] amb, float[] diff, float[] spec, float[] shiny) {
		if (log.isDebugEnabled()) {
			log.debug("New "+this.toString());
		}
		ambient = Buffers.newDirectFloatBuffer(amb);
		diffuse = Buffers.newDirectFloatBuffer(diff);
		specular = Buffers.newDirectFloatBuffer(spec);
		shinyness = Buffers.newDirectFloatBuffer(shiny);
	}
}
