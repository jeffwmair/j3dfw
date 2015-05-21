package com.jwm.j3dfw.geometry;

import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;

/**
 * A material contains colour components; determines how a Geometry looks
 * 
 * @author Jeff
 *
 */
public class Material {
	public FloatBuffer ambient;
	public FloatBuffer diffuse;
	public FloatBuffer specular;
	public FloatBuffer shinyness;

	private int ambSize, diffSize, specSize, shinySize;

	public Material(float[] amb, float[] diff, float[] spec, float[] shiny) {
		ambient = Buffers.newDirectFloatBuffer(amb);
		diffuse = Buffers.newDirectFloatBuffer(diff);
		specular = Buffers.newDirectFloatBuffer(spec);
		shinyness = Buffers.newDirectFloatBuffer(shiny);

		ambSize = amb.length;
		diffSize = diff.length;
		specSize = spec.length;
		shinySize = shiny.length;
	}

	public int getAmbientSize() {
		return ambSize;
	}
	public int getDiffuseSize() {
		return diffSize;
	}
	public int getSpecularSize() {
		return specSize;
	}
	public int getShinySize() {
		return shinySize;
	}
}
