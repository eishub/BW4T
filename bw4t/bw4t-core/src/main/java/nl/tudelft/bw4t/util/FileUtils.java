package nl.tudelft.bw4t.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A class of File operations.
 */
public class FileUtils {
	
	/**
	 * Copy a file to another file.
	 * @param toCopy the original file
	 * @param destFile the destination file
	 * @return true iff the file was copied correctly
	 */
	public static boolean copyFile(final File toCopy, final File destFile) {
		try {
			return FileUtils.copyStream(new FileInputStream(toCopy),
					new FileOutputStream(destFile));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * copy all of the files in the given directory .
	 * @param toCopy the folder to be copied
	 * @param destDir the folder to be copied to
	 * @return true iff the files were successfully copied
	 */
	private static boolean copyFilesRecusively(final File toCopy,
			final File destDir) {
		assert destDir.isDirectory();

		if (!toCopy.isDirectory()) {
			return FileUtils.copyFile(toCopy,
					new File(destDir, toCopy.getName()));
		} else {
			final File newDestDir = new File(destDir, toCopy.getName());
			if (!newDestDir.exists() && !newDestDir.mkdir()) {
				return false;
			}
			for (final File child : toCopy.listFiles()) {
				if (!FileUtils.copyFilesRecusively(child, newDestDir)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Copy resource files from a jar file to a directory 
	 * @param destDir the destination directory
	 * @param jarConnection the url of the folder in the jar to be copied
	 * @return true iff the file were successfully copied
	 * @throws IOException
	 */
	public static boolean copyJarResourcesRecursively(final JarURLConnection jarConnection, final File destDir) throws IOException {

		final JarFile jarFile = jarConnection.getJarFile();
		final String entryName = jarConnection.getEntryName();
		String entryNameParent = entryName.substring(0, entryName.lastIndexOf('/') + 1);
		
		System.out.println(entryNameParent);

		for (final Enumeration<JarEntry> e = jarFile.entries(); e
				.hasMoreElements();) {
			final JarEntry entry = e.nextElement();
			if (entry.getName().startsWith(entryName)) {
				final String filename = FileUtils.removeStart(entry.getName(), entryNameParent);

				final File f = new File(destDir, filename);
				if (!entry.isDirectory()) {
					final InputStream entryInputStream = jarFile
							.getInputStream(entry);
					if (!FileUtils.copyStream(entryInputStream, f)) {
						return false;
					}
					entryInputStream.close();
				} else {
					if (!FileUtils.ensureDirectoryExists(f)) {
						throw new IOException("Could not create directory: "
								+ f.getAbsolutePath());
					}
				}
			}
		}
		return true;
	}

	/**
	 * Copy files from local filesystem or resources to a folder in the filesystem.
	 * @param originUrl the origin url containing the files
	 * @param destination the folder to copy to
	 * @return true iff the files were copied successfully
	 */
	public static boolean copyResourcesRecursively( //
			final URL originUrl, final File destination) {
		try {
			final URLConnection urlConnection = originUrl.openConnection();
			if (urlConnection instanceof JarURLConnection) {
				return FileUtils.copyJarResourcesRecursively((JarURLConnection) urlConnection, destination);
			} else {
				return FileUtils.copyFilesRecusively(
						new File(originUrl.getPath()), destination);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Copy contents from the inputstream to the given file, closes the inputstream afterwards.
	 * @param is the inputstream
	 * @param f the outputfile
	 * @return true iff the file was successfully copied
	 */
	private static boolean copyStream(final InputStream is, final File f) {
		try {
			return FileUtils.copyStream(is, new FileOutputStream(f));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * this function copies the contents of the inputstream to the outputstream
	 * and closes both streams.
	 * 
	 * @param is
	 *            the inputstream
	 * @param os
	 *            the outputstream
	 * @return true iff the contents were successfully copied
	 */
	private static boolean copyStream(final InputStream is,
			final OutputStream os) {
		try {
			final byte[] buf = new byte[1024];

			int len = 0;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			os.close();
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * check whether the given directory exists or tries to make it.
	 * @param f the file to be checked
	 * @return true iff the directory exists or was created
	 */
	private static boolean ensureDirectoryExists(final File f) {
		return f.exists() || f.mkdir();
	}

	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}
}