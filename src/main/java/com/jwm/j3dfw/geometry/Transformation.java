package com.jwm.j3dfw.geometry;

import javax.media.opengl.GL2;

public abstract class Transformation {
	public enum TransformationType {
		scale, rotate, translate
	};

	protected TransformationType transType;

	public abstract void transform(GL2 gl);
	public TransformationType getType() {
		return transType;
	}
}
