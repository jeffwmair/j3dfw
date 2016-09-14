package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.production.Camera;

import java.util.List;

/**
 * Created by Jeff on 2016-09-13.
 */
public class VoxelGeometryFactory implements GeometryFactory {
    @Override
    public List<Geometry> buildGeometryItems() {
        throw new RuntimeException("Not impl");
    }

    @Override
    public Camera getMainCamera() {
        throw new RuntimeException("Not impl");
    }
}
