package com.jwm.j3dfw.geometry;

import com.jogamp.common.nio.Buffers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.nio.FloatBuffer;

/**
 * Geometric verticies for the shape as well as vertex normals for lighting
 * 
 * @author Jeff
 * 
 */
class Mesh {
	private static Logger log = LogManager.getLogger(Mesh.class);
	public FloatBuffer vertex_components;
	public FloatBuffer vertex_component_normals;
	private float[] vertex_components_arr;
	private int vertexCompSize, vertexNormalCompSize;

	public Mesh(float[] vertexComponents, float[] vertexComponentNormals) {
		if (log.isDebugEnabled()) {
			log.debug("new "+this.toString());
		}
		vertex_components_arr = vertexComponents;
		vertex_components = Buffers.newDirectFloatBuffer(vertexComponents);
		vertex_component_normals = Buffers.newDirectFloatBuffer(vertexComponentNormals);
		vertexCompSize = vertexComponents.length;
		vertexNormalCompSize = vertexComponentNormals.length;
	}
	public void getCenter(Vertex point) {
		float centerX = getCenterForComponent(0);
		float centerY = getCenterForComponent(1);
		float centerZ = getCenterForComponent(2);
		point.setX(centerX);
		point.setY(centerY);
		point.setZ(centerZ);
	}
	public Vertex getOffsetFromOrigin() {
		float x = getMaximumForComponent(0);
		float y = getMaximumForComponent(1);
		float z = getMaximumForComponent(2);
		log.debug("getOffsetFromOrigin()");
		return new Vertex(x, y, z);
	}
	private float getMaximumForComponent(int n) {
		float max = -999;
		for (int i = n; i < vertex_components_arr.length; i += 3) {
			float val = vertex_components_arr[i];
			if (val > max)
				max = val;
		}
		return max;
	}
	private float getCenterForComponent(int n) {
		float sum = 0;
		int count = 0;
		for (int i = n; i < vertex_components_arr.length; i += 3) {
			sum += vertex_components_arr[i];
			count++;
		}
		return sum / count;
	}
	public int getVertexComponentsSize() {
		return vertexCompSize;
	}
	public int getVertexNormalComponentsSize() {
		return vertexNormalCompSize;
	}
}
