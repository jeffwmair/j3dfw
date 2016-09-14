package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.controller.ControllerService;
import com.jwm.j3dfw.geometry.Geometry;

/**
 * Created by Jeff on 2016-09-14.
 */
public class VoxelControllerDirectoryImpl implements ControllerDirectory {
    private ControllerService service;

    public VoxelControllerDirectoryImpl() {
        service = new VoxelObjectController();
    }
    @Override
    public ControllerService getInstance(Geometry g) {
        return service;
    }
}
