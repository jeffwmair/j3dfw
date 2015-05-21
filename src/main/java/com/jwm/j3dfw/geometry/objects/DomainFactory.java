package com.jwm.j3dfw.geometry.objects;

import java.util.ArrayList;
import java.util.List;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;

public class DomainFactory {
	public static GeometryList getSingleTire() {
		GeometryList geo = new GeometryList();
		Plane p = new Plane();
		geo.add(p);
		Cube c = new Cube();
		geo.add(c);
		return geo;
	}

}
