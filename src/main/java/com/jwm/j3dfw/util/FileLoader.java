package com.jwm.j3dfw.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwm.j3dfw.geometry.Material;
import com.jwm.j3dfw.geometry.Mesh;

public class FileLoader {

	public static Map<String, Material> loadedMaterials;
	private static Map<String, Mesh> loadedMeshes;
	static {
		loadedMaterials = new HashMap<String, Material>();
		loadedMeshes = new HashMap<String, Mesh>();
	}

	public static Material loadMaterial(String resourceName, boolean useKdForAmbient) throws Exception {

		/*
		 * only load any material once, because its the same everywhere. For now
		 * anyway...
		 */
		String key = resourceName + Boolean.toString(useKdForAmbient);
		Material mat = loadedMaterials.get(key);
		if (mat != null)
			return mat;

		BufferedReader br = null;
		float[] ambient = new float[4];
		float[] diffuse = new float[4];
		float[] specular = new float[4];
		float[] shinyness = new float[1];

		InputStream is = getResourceStream(resourceName);

		try {
			br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();

			while (line != null) {
				if (isMaterialLine(line)) {
					String[] components = getLineComponents(line);
					if (isMaterialAmbient(line)) {
						for (int i = 0; i < 3; i++) {
							ambient[i] = Float.parseFloat(components[i]);
						}
					} else if (isMaterialDiffuse(line)) {
						for (int i = 0; i < 3; i++) {
							diffuse[i] = Float.parseFloat(components[i]);
							if (useKdForAmbient) {
								ambient[i] = Float.parseFloat(components[i]);
							}
						}
					} else if (isSpecularShininess(line)) {
						// only 1 component for shininess
						shinyness[0] = Float.parseFloat(components[0]);
					} else {
						for (int i = 0; i < 3; i++) {
							specular[i] = Float.parseFloat(components[i]);
						}
					}
				}
				line = br.readLine();
			}

			mat = new Material(ambient, diffuse, specular, shinyness);
			loadedMaterials.put(key, mat);
			return mat;

		} finally {
			br.close();
		}

	}
	public static Mesh loadMesh(String resourceName) throws Exception {

		InputStream is = getResourceStream(resourceName);
		
		String key = resourceName;
		Mesh m = loadedMeshes.get(key);
		if (m != null)
			return m;

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			String line = br.readLine();
			List<Float> allVertices = new ArrayList<Float>();
			List<Float> allNormals = new ArrayList<Float>();
			List<Float> verticesOnFaces = new ArrayList<Float>();
			List<Float> vertexNormalsOnFaces = new ArrayList<Float>();

			while (line != null) {
				String[] values;

				if (isMeshDataLine(line)) {
					values = getLineComponents(line);// line.substring(1).split(" ");

					if (isVertexOrVertexNormal(line)) {
						float x = Float.parseFloat(values[0]);
						float y = Float.parseFloat(values[1]);
						float z = Float.parseFloat(values[2]);
						if (isVertexLine(line)) {
							allVertices.add(x);
							allVertices.add(y);
							allVertices.add(z);
						} else {
							// vertex normal
							allNormals.add(x);
							allNormals.add(y);
							allNormals.add(z);
						}
					} else {
						/*
						 * must be a face. This means that we have read in all
						 * the verticies and vertex-normals. So we can find them
						 * in the previously populated lists.
						 */

						/*
						 * face components are separated by double slashes //.
						 * First component is the index of the vertex, second is
						 * index of the vertex-normal. Indexes are actually
						 * 1-based.
						 * 
						 * There will be 3 components because these are triangle
						 * poly's.
						 */

						int numEdgesForTriangle = 3;
						for (int i = 0; i < numEdgesForTriangle; i++) {
							String[] faceEdgeN = values[i].split("//");
							int vertexIndex = Integer.parseInt(faceEdgeN[0]);
							int vertexNormalIndex = Integer.parseInt(faceEdgeN[1]);

							// since they are 1-based, back the index off by 1
							vertexIndex--;
							vertexNormalIndex--;

							// look up the values that were found in the other
							// parsing section

							// putting x, y, and z into the other list
							for (int j = 0; j < 3; j++) {
								float vertexComponent = allVertices.get(3 * vertexIndex + j);
								verticesOnFaces.add(vertexComponent);
								float vertexNormalComponent = allNormals.get(3 * vertexNormalIndex + j);
								vertexNormalsOnFaces.add(vertexNormalComponent);
							}
						}
					}
				}
				line = br.readLine();
			}

			float[] vertex_components = new float[verticesOnFaces.size()];
			for (int i = 0; i < verticesOnFaces.size(); i++) {
				vertex_components[i] = verticesOnFaces.get(i);

			}

			float[] vertex_component_normals = new float[vertexNormalsOnFaces.size()];
			for (int i = 0; i < vertexNormalsOnFaces.size(); i++) {
				vertex_component_normals[i] = vertexNormalsOnFaces.get(i);
			}

			m = new Mesh(vertex_components, vertex_component_normals);

			loadedMeshes.put(key, m);
			return m;

		} finally {
			br.close();
		}
	}

	private static InputStream getResourceStream(String resourcename) {
		InputStream is = null;
		try {
			is = FileLoader.class.getClassLoader().getResourceAsStream("graphics/" + resourcename);
			if (is == null) { 
				throw new RuntimeException("Failed to load resource: " + resourcename);
			}
			return is;
		}
		catch(Exception ex) {
			throw new RuntimeException("Failed to load resource: " + resourcename);
		}
	}
	
	private static String[] getLineComponents(String line) throws Exception {
		if (line.startsWith("v ") || line.startsWith("f ")) {
			return line.substring(2).split(" ");
		} else if (line.startsWith("vn ") || line.startsWith("Ka ") || line.startsWith("Ks ") || line.startsWith("Kd ") || line.startsWith("Ns")) {
			return line.substring(3).split(" ");
		} else {
			throw new Exception("Failed to parse the line:" + line);
		}
	}

	private static boolean isMaterialLine(String line) {
		return isMaterialAmbient(line) || isMaterialDiffuse(line) || isMaterialSpecular(line)
				|| isSpecularShininess(line);
	}

	private static boolean isMaterialAmbient(String line) {
		return line.startsWith("Ka");
	}

	private static boolean isMaterialDiffuse(String line) {
		return line.startsWith("Kd");
	}

	private static boolean isMaterialSpecular(String line) {
		return line.startsWith("Ks");
	}

	private static boolean isSpecularShininess(String line) {
		return line.startsWith("Ns");
	}

	private static boolean isVertexOrVertexNormal(String line) {
		return isVertexLine(line) || isVertexNormalLine(line);
	}

	private static boolean isVertexLine(String line) {
		return line.startsWith("v ");
	}

	private static boolean isVertexNormalLine(String line) {
		return line.startsWith("vn ");
	}

	private static boolean isFaceLine(String line) {
		return line.startsWith("f ");
	}

	private static boolean isMeshDataLine(String line) {
		return isVertexLine(line) || isVertexNormalLine(line) || isFaceLine(line);
	}
}
