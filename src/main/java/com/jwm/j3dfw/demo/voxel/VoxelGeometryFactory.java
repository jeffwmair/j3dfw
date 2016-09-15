package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.demo.RotatingPlane;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.geometry.shapes.Cube;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.TargetCamera;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 2016-09-13.
 */
class VoxelGeometryFactory implements GeometryFactory {

    TargetCamera targetCam;
    List<Geometry> geometryList = new ArrayList<>();

    VoxelGeometryFactory() {
        RotatingPlane plane = new RotatingPlane();


        Cube c = new VoxelPerson();
        geometryList.add(c);


        plane.initCamera();
        targetCam = plane.getCamera();
        targetCam.setZoom(100);
    }

    @Override
    public List<Geometry> buildGeometryItems() {
        return geometryList;
    }

    @Override
    public Camera getMainCamera() {
        return targetCam;
    }
}
