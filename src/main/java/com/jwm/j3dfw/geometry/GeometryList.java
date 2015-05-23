package com.jwm.j3dfw.geometry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeometryList implements Iterable<Geometry> {

	private List<Geometry> list;

	public GeometryList() {
		list = new ArrayList<Geometry>();
	}
	public void add(Geometry item) {
		list.add(item);
	}
	public <T> Geometry firstOrNull(Class<T> type) {
		for (Geometry geometry : list) {
			if (type.isInstance(geometry)) {
				return geometry;
			}
		}
		return null;
	}
	@Override
	public Iterator<Geometry> iterator() {
		return list.iterator();
	}

}
