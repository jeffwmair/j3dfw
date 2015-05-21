package com.jwm.j3dfw.production;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.utils.Shader;

public class Scene implements GLEventListener {
	public Camera camera;
	private GLU glu;
	private GeometryList sceneProps;
	private float viewportWidth;
	protected double mouseXPositionRelativeToCenter;

	public Scene(GeometryList sceneItems, Camera camera) {
		sceneProps = sceneItems;
		glu = new GLU();
		this.camera = camera;
		this.camera.setGlu(glu);
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		GL2 gl2 = gl.getGL2();
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		Lights.setupLighting(gl2);
		camera.Update(gl2, glu);
		for (Geometry item : sceneProps) {
			item.render(gl2, glu);
		}
	}
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		float backColor = 1.0f;
		gl.glClearColor(backColor, backColor, backColor, backColor);
		Lights.setupLighting(gl);
		Shader.initShaders(gl, "vertexshader.txt", "fragmentshader.txt");
	}
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
