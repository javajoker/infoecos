package com.infoecos.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * OS/File utilities
 * 
 * @author Ning Hu
 * 
 */
public class FileUtil {
	/**
	 * Create temporary folder/directory
	 * 
	 * @return
	 * @throws IOException
	 */
	public static File createTempDirectory() throws IOException {
		final File temp = File.createTempFile("temp",
				Long.toString(System.nanoTime()));
		if (!(temp.delete()))
			throw new IOException("Could not delete temp file: "
					+ temp.getAbsolutePath());
		if (!(temp.mkdir()))
			throw new IOException("Could not create temp directory: "
					+ temp.getAbsolutePath());

		return temp;
	}

	/**
	 * Get sub files in specified folder, specify file extension if necessary
	 * 
	 * @param folder
	 * @param extensions
	 *            extensions filter, separate with "|", "." is required
	 * @return
	 */
	public static File[] getFiles(URL folder, String extensions) {
		return getFiles(folder.getPath(), extensions);
	}

	/**
	 * Get sub files in specified folder, specify file extension if necessary
	 * 
	 * @param folder
	 * @param extensions
	 *            extensions filter, separate with "|", "." is required
	 * @return
	 */
	public static File[] getFiles(String folder, String extensions) {
		File dataFile = null;
		try {
			dataFile = new File(String.format("%s%s",
					ResourceLocator.getBasePath(), folder));
			if (!dataFile.exists())
				dataFile = new File(String.format("%s%s",
						ResourceLocator.getRuntimePath(), folder));
			if (!dataFile.exists())
				dataFile = new File(folder);
			if (!dataFile.exists())
				System.err.println("Illegal resource.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		List<File> files = new ArrayList<File>();
		FileUtil.enumFiles(dataFile.getPath(), extensions, files);
		return (File[]) files.toArray(new File[files.size()]);
	}

	/**
	 * Enumerate files under certain folder, recursively
	 * 
	 * @param path
	 * @param extensions
	 *            extensions filter, separate with "|", "." is required
	 * @param files
	 *            output files
	 */
	public static void enumFiles(String path, String extensions,
			List<File> files) {
		final String[] exts = extensions.split("\\|");
		File parent = new File(path);
		if (parent.isFile()) {
			files.add(parent);
			return;
		}
		for (File f : parent.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				for (String ext : exts) {
					int idx = pathname.getName().lastIndexOf('.');
					if (ext.equals(idx < 0 ? "" : pathname.getName().substring(
							idx))) {
						return true;
					}
				}
				return false;
			}
		})) {
			enumFiles(f.getPath(), extensions, files);
		}
	}

	/**
	 * Calculate hash string of given file
	 * 
	 * @param fis
	 * @param hashType
	 *            e.g., "MD5"
	 * @return
	 * @throws Exception
	 */
	public static String getFileHash(InputStream fis, String hashType)
			throws Exception {
		byte buffer[] = new byte[1024];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		for (int numRead = 0; (numRead = fis.read(buffer)) > 0;) {
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return toHexString(md5.digest());
	}

	private static char hexChar[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * Byte array to Hex string
	 * 
	 * @param b
	 * @return
	 */
	public static String toHexString(byte b[]) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0xf]);
		}

		return sb.toString();
	}
}
