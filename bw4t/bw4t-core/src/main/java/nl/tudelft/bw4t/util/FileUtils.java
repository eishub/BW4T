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
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * A class of File operations.
 */
public final class FileUtils {
    
    /** 
     * error message used when could not find file 
     */ 
    private static final String COULD_NOT_FIND_FILE = "Could not find '%s'";

    /**
     * The log4j logger, logs to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class);

    /**
     * error message used when we failed to close a file.
     */
    private static final String FAILED_TO_CLOSE = "Failed to close file '%s'";

    /**
     * Utility class, cannot be instantiated.
     */
    private FileUtils() {
    }

    /**
     * Copy a file to another file.
     * 
     * @param toCopy
     *            the original file
     * @param destFile
     *            the destination file
     * @return true iff the file was copied correctly
     */
    public static boolean copyFile(final File toCopy, final File destFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(toCopy);
            fos = new FileOutputStream(destFile);
            return FileUtils.copyStream(fis, fos);
        } catch (final FileNotFoundException e) {
            LOGGER.error(String.format("Failed to copy file '%s' to '%s'", toCopy, destFile), e);
        } finally {
            close(toCopy, fis, fos);
        }
        return false;
    }

    /**
     * Closes FileInputStream and/or FileOutpusStream 
     *             (when the exist/not null)
     * 
     * @param fis FileInputStream
     * @param fos FileOutputStream
     * @param toCopy original file
     */
    private static void close(final File toCopy, FileInputStream fis,
            FileOutputStream fos) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                LOGGER.warn(String.format(FAILED_TO_CLOSE, toCopy), e);
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                LOGGER.warn(String.format(FAILED_TO_CLOSE, toCopy), e);
            }
        }
    }

    /**
     * copy all of the files in the given directory .
     * 
     * @param toCopy
     *            the folder to be copied
     * @param destDir
     *            the folder to be copied to
     * @return true iff the files were successfully copied
     */
    private static boolean copyFilesRecusively(final File toCopy, final File destDir) {
        assert destDir.isDirectory();

        if (!toCopy.isDirectory()) {
            return FileUtils.copyFile(toCopy, new File(destDir, toCopy.getName()));
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
     * 
     * @param destDir
     *            the destination directory
     * @param jarConnection
     *            the url of the folder in the jar to be copied
     * @return true iff the file were successfully copied
     * @throws IOException 
     */
    public static boolean copyJarResourcesRecursively(final JarURLConnection jarConnection, final File destDir)
            throws IOException {

        final JarFile jarFile = jarConnection.getJarFile();
        final String entryName = jarConnection.getEntryName();
        String entryNameParent = entryName.substring(0, entryName.lastIndexOf('/') + 1);

        for (final Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements();) {
            final JarEntry entry = e.nextElement();
            if (entry.getName().startsWith(entryName)) {
                boolean copy = copyJar(destDir, jarFile, entryNameParent, entry);
                if (!copy)
                    return copy;
            }
        }
        return true;
    }

    /**
     * Copy resource files from a jar file to a directory
     * 
     * @param destDir
     *            the destination directory
     * @param jarFile 
     * @param entryNameParent 
     * @param entry JarEntry
     * @return true iff the file were successfully copied
     * @throws IOException 
     */
    private static boolean copyJar(final File destDir, final JarFile jarFile,
            String entryNameParent, final JarEntry entry) throws IOException {
        
        final String filename = FileUtils.removeStart(entry.getName(), entryNameParent);

        final File f = new File(destDir, filename);
        if (!entry.isDirectory()) {
            final InputStream entryInputStream = jarFile.getInputStream(entry);
            if (!FileUtils.copyStream(entryInputStream, f)) {
                return false;
            }
            entryInputStream.close();
        } else {
            if (!FileUtils.ensureDirectoryExists(f)) {
                throw new IOException("Could not create directory: " + f.getAbsolutePath());
            }
        }
        return true;
    }

    /**
     * Copy files from local filesystem or resources to a folder in the filesystem.
     * 
     * @param originUrl
     *            the origin url containing the files
     * @param destination
     *            the folder to copy to
     * @return true iff the files were copied successfully
     */
    public static boolean copyResourcesRecursively(final URL originUrl, final File destination) {
        try {
            final URLConnection urlConnection = originUrl.openConnection();
            if (urlConnection instanceof JarURLConnection) {
                return FileUtils.copyJarResourcesRecursively((JarURLConnection) urlConnection, destination);
            } else {
                return FileUtils.copyFilesRecusively(new File(URLDecoder.decode(originUrl.getPath(), "UTF-8")),
                        destination);
            }
        } catch (final IOException e) {
            LOGGER.error("Failed to copy files from Jar", e);
        }
        return false;
    }

    /**
     * Copy contents from the inputstream to the given file, closes the inputstream afterwards.
     * 
     * @param is
     *            the inputstream
     * @param f
     *            the outputfile
     * @return true iff the file was successfully copied
     */
    private static boolean copyStream(final InputStream is, final File f) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            return FileUtils.copyStream(is, fos);
        } catch (final FileNotFoundException e) {
            LOGGER.warn(String.format(COULD_NOT_FIND_FILE, f), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LOGGER.warn(String.format(FAILED_TO_CLOSE, f), e);
                }
            }
        }
        return false;
    }

    /**
     * this function copies the contents of the inputstream to the outputstream and closes both streams.
     * 
     * @param is
     *            the inputstream
     * @param os
     *            the outputstream
     * @return true iff the contents were successfully copied
     */
    private static boolean copyStream(final InputStream is, final OutputStream os) {
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
            LOGGER.error("Failed to copy stream", e);
        }
        return false;
    }

    /**
     * check whether the given directory exists or tries to make it.
     * 
     * @param f
     *            the file to be checked
     * @return true iff the directory exists or was created
     */
    private static boolean ensureDirectoryExists(final File f) {
        return f.exists() || f.mkdir();
    }

    /**
     * 
     * @param str Original String
     * @param remove String to remove 
     * @return rest of string after removing remove in front
     *      when str does not start with remove, return str
     */
    public static String removeStart(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)) {
            return str.substring(remove.length());
        }
        return str;
    }

    /**
     * @param cs CharSequence to check
     * @return true iff cs is null or has length zero
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    
    /**
     * Checks whether the file name has the required extension.
     * @param fileName The file name including extension.
     * @param extensionRequired The extension required for the file name.
     * @return Whether the file name has the required extension.
     */
    public static boolean hasRequiredExtension(String fileName, String extensionRequired) {
        return getExtension(fileName).equals(extensionRequired);
    }
    
    /**
     * Gets the extension, including the starting dot, of a file name.
     * @param fileName The file name including extension.
     * @return The extension of the file.
     */
    public static String getExtension(String fileName) {
        String[] splitFileName = fileName.split("\\.");
        if (splitFileName.length <= 1) {
            return "";
        }
        int lastDotIndex = splitFileName.length - 1;
        return "." + splitFileName[lastDotIndex];
    }
    
    /**
     * Gets the file name without it's extension.
     * @param fileName The file name.
     * @return The file name without extension.
     */
    public static String getFileNameWithoutExtension(String fileName) {
        int extensionLength = getExtension(fileName).length();
        return fileName.substring(0, fileName.length() - extensionLength);
    }
    
}
