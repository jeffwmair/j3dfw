package com.jwm.j3dfw.geometry;

import com.jwm.j3dfw.geometry.Transition.TransitionType;
import com.jwm.j3dfw.production.TargetCamera;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import java.util.ArrayList;
import java.util.List;

public class Geometry {

	/**
	 * TODO: simplify this class; it is too big
	 */

	private static Logger log = LogManager.getLogger(Geometry.class);

	protected List<Geometry> children;
	private Mesh mesh;
	private Material material;
	private List<Transformation> transformations;
	private Vertex offsetFromOrigin;
	private Rotation rotationOverhead, rotationLeftAndRight, rotationEndOverEnd, rotationPostTranslate;
	private Transition rotationOverheadTransition, rotationLeftAndRightTransition, rotationEndOverEndTransition;
	private Translation overallTranslation;
	private Scaling overallScale;
	private TargetCamera cam;
	private List<GeometryListener> listeners;

	public Geometry() {
		// todo: come up with something better
		this(null, null);
		if (log.isDebugEnabled()) {
			log.debug("New "+this.toString());
		}
	}

	public Geometry(String meshFilePrefix, String materialFilePrefix) {
		if (log.isDebugEnabled()) {
			log.debug("new Geometry:" + this);
		}
		listeners = new ArrayList<>();
		children = new ArrayList<>();
		transformations = new ArrayList<>();
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
		log.debug("Geometry new vertex");
		offsetFromOrigin = new Vertex(0, 0, 0);

		// todo: i don't like this
		if (meshFilePrefix != null && materialFilePrefix != null) {
			loadRootPart(meshFilePrefix, materialFilePrefix);
		}
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
	protected void increaseTranslation(double x, double y, double z) {
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
	protected void transitionRotation(double startAngle, double endAngle, Rotation.RotationDirection dir,
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
	public void getCenter(Vertex point) {

		Mesh m = mesh;
		if (m == null) {
			if (children == null || children.size() == 0) {
				throw new RuntimeException("Cannot get the center for this ("+this+") because it has no mesh and has no child geometry items.");
			}
			m = children.get(0).mesh;
		}
		m.getCenter(point);
		overallTranslation.transformVertex(point);
	}
	public void getNearbyPointOnYPlane(Vertex point, double distanceFromCenter, double angleOverheadRotation) {
		getCenter(point);
		double radians = Math.toRadians(angleOverheadRotation - rotationOverhead.getAngle());
		double x = distanceFromCenter * Math.cos(radians);
		double z = distanceFromCenter * Math.sin(radians);
		point.setX(point.getX()+x);
		point.setZ(point.getZ()+z);
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
		int ITEMS_PER_VERTEX = 3;
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
	private void loadRootPart(String meshFilePrefix, String materialFilePrefix) {
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
		log.debug("getOffsetFromOrigin()");
		offsetFromOrigin = new Vertex(x, y, z);
	}

	@Override
	public String toString() {
		return super.toString() + ", offsetFromOrigin:" + offsetFromOrigin;	
	}
}
