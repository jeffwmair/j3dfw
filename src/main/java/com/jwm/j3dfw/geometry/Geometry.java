package com.jwm.j3dfw.geometry;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jwm.j3dfw.geometry.Transition.TransitionType;
import com.jwm.j3dfw.production.TargetCamera;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.util.FileLoader;

public class Geometry {

	/**
	 * TODO: simplify this class; it is too big
	 */

	private static Logger log = LogManager.getLogger(Geometry.class);

	protected List<Geometry> children;
	private final int ITEMS_PER_VERTEX = 3;
	private Mesh mesh;
	private Material material;
	private List<Transformation> transformations;
	private Vertex offsetFromOrigin;
	private Rotation rotationOverhead, rotationLeftAndRight, rotationEndOverEnd, rotationPostTranslate;
	private Transition rotationOverheadTransition, rotationLeftAndRightTransition, rotationEndOverEndTransition;
	private Translation overallTranslation;
	private Scaling overallScale;
	protected TargetCamera cam;
	protected List<GeometryListener> listeners;

	public Geometry() {
		if (log.isDebugEnabled()) {
			log.debug("new Geometry:" + this);
		}
		listeners = new ArrayList<GeometryListener>();
		children = new ArrayList<Geometry>();
		transformations = new ArrayList<Transformation>();
		overallTranslation = new Translation(0, 0, 0);

		rotationOverhead = Rotation.getOverheadRotation();
		rotationLeftAndRight = Rotation.getRotationOnZVector();
		rotationEndOverEnd = Rotation.getRotationOnXVector();
		rotationPostTranslate = new Rotation(0, Rotation.RotationDirection.endToEnd);
		overallScale = new Scaling(1, 1, 1);

		rotationEndOverEndTransition = new Transition();
		rotationLeftAndRightTransition = new Transition();
		rotationOverheadTransition = new Transition();

		transformations.add(rotationPostTranslate);
		// overall translation generally should be done last.
		transformations.add(overallTranslation);
		transformations.add(rotationOverhead);
		transformations.add(rotationEndOverEnd);
		transformations.add(rotationLeftAndRight);
		transformations.add(overallScale);
		offsetFromOrigin = new Vertex(0, 0, 0);
	}
	public void registerInfoListener(GeometryListener listener) {
		if (log.isDebugEnabled()) {
			log.debug("registerInfoListener:" + listener);
		}

		listeners.add(listener);
	}
	protected void writeInfoToListeners(List<String> info) {
		for (GeometryListener listener : listeners) {
			listener.writeInfo(info);
		}
	}
	public void setOverallTranslation(double x, double y, double z) {
		overallTranslation.setValues(x, y, z);
	}
	public void increaseTranslation(double x, double y, double z) {
		overallTranslation.updateByAmount(x, y, z);
	}
	public void setRotation(double angle, Rotation.RotationDirection dir) {
		getRotation(dir).setAngle(angle);
	}
	private Transition getRotationTransition(Rotation.RotationDirection dir) {
		switch (dir) {
			case endToEnd:
				return rotationEndOverEndTransition;
			case leftAndRight:
				return rotationLeftAndRightTransition;
			case overhead:
				return rotationOverheadTransition;
			default:
				return rotationEndOverEndTransition;
		}
	}
	public Rotation getRotation(Rotation.RotationDirection dir) {
		switch (dir) {
			case endToEnd:
				return rotationEndOverEnd;
			case leftAndRight:
				return rotationLeftAndRight;
			case overhead:
				return rotationOverhead;
			default:
				return rotationEndOverEnd;
		}
	}
	public Rotation getPostTranslateRotation() {
		return rotationPostTranslate;
	}
	public void transitionRotation(double startAngle, double endAngle, Rotation.RotationDirection dir,
			int speedOutOf10, TransitionType transType) {
		Transition appropriateTransition = null;
		try {
			appropriateTransition = getRotationTransition(dir);
			appropriateTransition.startTransition(startAngle, endAngle, speedOutOf10, transType);

		} catch (Exception e) {
			log.error("startAngle:"+startAngle+",endAngle:"+endAngle+", dir:"+dir, e);
		}
		getRotation(dir).setAngle(startAngle);
	}
	public void setScale(double scale) {
		overallScale.set(scale);
	}
	public void setScale(double x, double y, double z) {
		overallScale.set(x, y, z);
	}
	public void addChild(Geometry child) {
		if (log.isDebugEnabled()) {
			log.debug("addChild: adding " + child + " to this: " + this);
		}
		children.add(child);
	}
	public TargetCamera getCamera() {
		return cam;
	}
	public void initCamera() {
		if (log.isDebugEnabled()) {
			log.debug("initCamera()");
		}
		cam = new TargetCamera();
		cam.setTargetGeometry(this);
	}
	public Vertex getCenter() {
		if (log.isDebugEnabled()) {
			log.debug("getCenter");
		}

		Mesh m = mesh;
		if (m == null) {
			if (log.isDebugEnabled()) {
				log.debug("m is null, trying to get the first child's mesh");
			}
			if (children == null || children.size() == 0) {
				throw new RuntimeException("Cannot get the center for this ("+this+") because it has no mesh and has no child geometry items.");
			}
			m = children.get(0).mesh;
		}
		Vertex meshCenter = m.getCenter();
		Vertex center = overallTranslation.getTransformedVertex(meshCenter);
		return center;
	}
	public Vertex getNearbyPointOnYPlane(double distanceFromCenter, double angleOverheadRotation) {
		Vertex center = getCenter();
		Vertex nearby = new Vertex(center.x, center.y, center.z);
		double radians = Math.toRadians(angleOverheadRotation - rotationOverhead.getAngle());
		double x = distanceFromCenter * Math.cos(radians);
		double z = distanceFromCenter * Math.sin(radians);
		nearby.x += x;
		nearby.z += z;
		return nearby;
	}
	public Rotation addRotation(double angle, Rotation.RotationDirection dir) {
		Rotation r = new Rotation(angle, dir);
		transformations.add(r);
		return r;
	}
	public void render(GL2 gl, GLU glu) {
		applyLogic();
		setMaterials(gl);
		gl.glPushMatrix();
		applyTransformations(gl);
		setVerticies(gl);
		for (Geometry c : children) {
			c.render(gl, glu);
		}
		gl.glPopMatrix();
	}
	protected void applyLogic() {
		// override if you want logic
	}
	private void applyTransformations(GL2 gl2) {
		/*
		 * first move to the origin because some parts don't actually sit at the
		 * origin; eg, hood, doors, etc.
		 */
		gl2.glTranslated(offsetFromOrigin.x, offsetFromOrigin.y, offsetFromOrigin.z);
		for (Transformation transformation : transformations) {
			transformation.transform(gl2);
		}

		/*
		 * TODO: generalize the following; compose rotations inside
		 * transformations, and put into a list
		 */
		if (rotationEndOverEndTransition.isInProgress()) {
			rotationEndOverEnd.setAngle(rotationEndOverEndTransition.getNext());
		}
		if (rotationLeftAndRightTransition.isInProgress()) {
			rotationLeftAndRight.setAngle(rotationLeftAndRightTransition.getNext());
		}
		if (rotationOverheadTransition.isInProgress()) {
			rotationOverhead.setAngle(rotationOverheadTransition.getNext());
		}
		gl2.glTranslated(-offsetFromOrigin.x, -offsetFromOrigin.y, -offsetFromOrigin.z);
	}
	private void setVerticies(GL2 gl2) {
		if (mesh == null)
			return;
		gl2.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		gl2.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		int vertexCount = mesh.getVertexComponentsSize() / ITEMS_PER_VERTEX;
		gl2.glNormalPointer(GL.GL_FLOAT, 0, mesh.vertex_component_normals);
		gl2.glVertexPointer(ITEMS_PER_VERTEX, GL.GL_FLOAT, 0, mesh.vertex_components);
		gl2.glDrawArrays(GL.GL_TRIANGLES, 0, vertexCount);
		gl2.glDisableClientState(GL2.GL_NORMAL_ARRAY);
		gl2.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	}
	private void setMaterials(GL2 gl2) {
		if (material == null)
			return;
		gl2.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, material.ambient);
		gl2.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, material.specular);
		gl2.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, material.diffuse);
		gl2.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, material.shinyness);
	}
	public void loadRootPart(String meshFilePrefix, String materialFilePrefix) {
		try {
			mesh = FileLoader.loadMesh(meshFilePrefix + ".obj");
			material = FileLoader.loadMaterial(materialFilePrefix + ".mtl", true);
			Vertex offset = mesh.getOffsetFromOrigin();
			setOffsetFromOrigin(offset.x, offset.y, offset.z);
		} catch (Exception e) {
			log.error("Failed to load part:" + e.toString(), e);
		}
	}
	protected void setOffsetFromOrigin(double x, double y, double z) {
		offsetFromOrigin = new Vertex(x, y, z);
	}

	@Override
	public String toString() {
		return super.toString() + ", offsetFromOrigin:" + offsetFromOrigin;	
	}
}
