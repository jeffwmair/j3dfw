package com.jwm.j3dfw.geometry;

import com.jwm.j3dfw.production.Camera;

import java.util.List;

/**
 * Responsible for creating all the geometry objects
 * Created by Jeff on 2016-04-02.
 */
public interface GeometryFactory {

    List<Geometry> buildGeometryItems();
    Camera getMainCamera();
}
