package com.jwm.j3dfw.demo.voxel;

import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.demo.ControllerDirectoryDefaultImpl;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.util.MainFrame;

/**
 * Created by Jeff on 2016-09-13.
 */
public class VoxelDemo {

    public static void main (String args[]) {
        ControllerDirectory cd = new ControllerDirectoryDefaultImpl();
        GeometryFactory geometryFactory = new VoxelGeometryFactory();
        int targetFps = 60;
        int frameWidth = 800;
        int frameHeight = 800;
        MainFrame.startMainFrame(geometryFactory, cd, targetFps, frameWidth, frameHeight);
    }
}