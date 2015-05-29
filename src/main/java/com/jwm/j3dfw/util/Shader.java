package com.jwm.j3dfw.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Shader {

	private static Logger log = LogManager.getLogger(Shader.class);

	/**
	 * Setup vertex and fragment shaders
	 */
	public static void initShaders(GL2 gl, String vertexShaderResource, String fragmentShaderResource) {
		log.info("initShaders");

		int v = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		int f = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

		ClassLoader cl = Shader.class.getClassLoader();
		InputStream vertexShaderInStream = FileLoader.class.getClassLoader().getResourceAsStream("graphics/" + vertexShaderResource);
		if (vertexShaderInStream == null) {
			throw new RuntimeException("Could not find the vertex shader resource named: " + vertexShaderResource);
		}
		InputStream fragmentShaderInStream= FileLoader.class.getClassLoader().getResourceAsStream("graphics/" + fragmentShaderResource);
		if (fragmentShaderInStream == null) {
			throw new RuntimeException("Could not find the fragment shader resource named: " + fragmentShaderResource);
		}

		String vsrc = readFromFile(vertexShaderInStream);
		gl.glShaderSource(v, 1, new String[] { vsrc }, (int[]) null, 0);
		gl.glCompileShader(v);

		String fsrc = readFromFile(fragmentShaderInStream);
		gl.glShaderSource(f, 1, new String[] { fsrc }, (int[]) null, 0);
		gl.glCompileShader(f);

		int shaderprogram = gl.glCreateProgram();
		gl.glAttachShader(shaderprogram, v);
		gl.glAttachShader(shaderprogram, f);
		gl.glLinkProgram(shaderprogram);
		gl.glValidateProgram(shaderprogram);

		gl.glUseProgram(shaderprogram);

		// timeUniform = gl.glGetUniformLocation(shaderprogram, "time");
	}

	private static String readFromFile(InputStream inStream) {
		if (log.isDebugEnabled()) {
			log.debug("readFromFile...");
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inStream));
			String line = br.readLine();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			br.close();
			return sb.toString();

		} catch (Exception ex) {
			log.error("Failed to read shader content from inputstream", ex);
			return null;
		}
	}

}
