package com.infoecos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * Easy class to get resource location
 * 
 * @author DCH
 * 
 */
public class ResourceLocator {
	public static boolean _VERBOSE = true;

	/**
	 * Get base path of webapp/project
	 * 
	 * @return
	 */
	public static String getBasePath() {
		File file = new File(getRuntimePath());
		return file.getParent() + File.separatorChar;
	}

	/**
	 * Get runtime path, e.g., webapp/WEB-INF/classes
	 * 
	 * @return
	 */
	public static String getRuntimePath() {
		// return ResourceLocator.class.getResource("/").getPath();
		return ResourceLocator.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
	}

	/**
	 * @param fileName
	 *            file name, based on runtime path
	 * @return the file name of this runtime resource, or an empty string if one
	 *         does not exist
	 */
	public static String getRuntimePath(String fileName) {
		return ResourceLocator.class.getResource(fileName).getFile();
	}

	/**
	 * @param fileName
	 *            file name, based on base path
	 * @return
	 */
	public static String getBasePath(String fileName) {
		return String.format("%s%s", getBasePath(), fileName);
	}

	/**
	 * Try to find resource path by base path, absolute path and runtime path
	 * 
	 * @param fileName
	 *            resource name
	 * @return
	 */
	public static String getPath(String fileName) {
		File f = new File(getBasePath(fileName));
		if (!f.exists())
			f = new File(fileName);
		if (!f.exists())
			f = new File(getRuntimePath(fileName));
		return f.getPath();
	}

	/**
	 * Whether file exists or not
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean exists(String fileName) {
		File f = new File(getBasePath(fileName));
		if (f.exists())
			return true;
		f = new File(fileName);
		if (f.exists())
			return true;
		f = new File(getRuntimePath(fileName));
		if (f.exists())
			return true;
		return false;
	}

	/**
	 * Get runtime resource in stream
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getRuntimeSource(String fileName) {
		return ResourceLocator.class.getResourceAsStream(fileName);
	}

	/**
	 * Get base resource in stream
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getBaseSource(String fileName) {
		try {
			return new FileInputStream(getBasePath(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get resource in steam, tried to lookup in base path, then runtime path
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getSource(String fileName) {
		InputStream is = getBaseSource(fileName);
		if (is == null)
			try {
				is = new URL(String.format("file://%s", fileName)).openStream();
			} catch (Exception e) {
				is = null;
			}
		if (is == null)
			is = getRuntimeSource(fileName);
		return is;
	}
}
