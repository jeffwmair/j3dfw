package com.jwm.j3dfw.production;

import com.jwm.j3dfw.geometry.Geometry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.util.List;

public class Scene implements GLEventListener {
	private static Logger log = LogManager.getLogger(Scene.class);
	private Camera camera;
	private GLU glu;
	private List<Geometry> sceneProps;
	private float viewportWidth;

	public Scene(List<Geometry> sceneItems, Camera camera) {
		log.info("New scene with camera:"+camera+", and sceneItems:" + sceneItems);
		sceneProps = sceneItems;
		glu = new GLU();
		this.camera = camera;
	}

	/**
	 * GLEventListener main callback loop
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		GL2 gl2 = gl.getGL2();
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		Lights.setupLighting(gl2);
		camera.update(gl2, glu);
		for (Geometry item : sceneProps) {
			item.render(gl2, glu);
		}
	}

	/**
	 * Initial setup of the GLEventListener callback
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		log.debug("GLEventListener init callback");
		GL2 gl = drawable.getGL().getGL2();
		float backColor = 1.0f;
		gl.glClearColor(backColor, backColor, backColor, backColor);
		Lights.setupLighting(gl);
		Shader.initShaders(gl, "vertexshader.txt", "fragmentshader.txt");
	}

	/**
	 * GLEventListener callback during window frame reshape
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		viewportWidth = width;
		GL gl = drawable.getGL();
		gl.glViewport(0, 0, width, height);
		camera.updateViewportDimensions(width, height);
	}
	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

	public double getViewportWidth() { 
		return viewportWidth;
	}
}
