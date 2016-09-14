package com.jwm.j3dfw.demo.buildings;

import com.jwm.j3dfw.geometry.*;
import com.jwm.j3dfw.geometry.shapes.Cube;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.TargetCamera;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jeff on 2016-04-02.
 */
public class DemoGeometryFactoryImpl implements GeometryFactory {

    TargetCamera targetCam = null;

    @Override
    public List<Geometry> buildGeometryItems() {
        List<Geometry> parts = new ArrayList<>();
        RotatingPlane plane = new RotatingPlane();
        parts.add(plane);

        Random rand = new Random(System.currentTimeMillis());

        // y -> vertical height
        // z -> distance from the camera;
        double z = -3.0;
        double xRange = 3.0;
        double zRange = 5.0;
        for (int i = 0; i < 50; i++) {
            Cube tower = new Cube();
            tower.setRotation(180.0, Rotation.RotationDirection.endToEnd);
            // move the tower placement
            double randomX = getRandom(-xRange, xRange, rand);
            double randomZ = getRandom(-zRange, zRange, rand);
            randomZ -= 7.0;
            double randomHeight = getRandom(1.0, 4.5, rand);
            tower.setOverallTranslation(randomX, -0.95, randomZ);
            tower.setScale(0.25, randomHeight, 0.25);
            plane.addChild(tower);
        }

        plane.initCamera();
        targetCam = plane.getCamera();
        targetCam.toggleAutoTrack();

        Vertex camPos = targetCam.getPosition();
        Vertex camTarget = targetCam.getTarget();
        camTarget.setY(camTarget.getY()+20);
        camPos.setY(20);

        return parts;
    }

    private static double getRandom(double min, double max, Random r) {
        double randDbl = r.nextDouble();
        return scaleVal(min, max, randDbl);
    }

    private static double scaleVal(double min, double max, double val) {
		double newRange = max-min;
		double oldRange = 1.0; // java nextDouble...
		return newRange * val + min;
	}

    @Override
    public Camera getMainCamera() {
        if (targetCam == null) {
            throw new RuntimeException("target cam was not yet initialized!");
        }
        return targetCam;
    }
}
