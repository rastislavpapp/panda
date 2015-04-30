package eu.nyerel.panda.ijplugin.runner;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class JarUtils {

	private static final Logger LOGGER = Logger.getInstance(JarUtils.class);
	private static final String PANDA_AGENT_JAR_FILE_NAME = "panda-agent.jar";
	private static final int EOF = -1;

	private JarUtils() {
	}

	public static String geUnpackedResourcePath(String resourcePath) {
		InputStream is = JarUtils.class.getResourceAsStream(resourcePath);
		if (is == null) {
			throw new IllegalArgumentException("Resource not found on path " + resourcePath);
		}
		File tempFile = getAsTempFile(is);
		return tempFile.getAbsolutePath();
	}

	private static File getAsTempFile(InputStream is) {
		OutputStream os = null;
		File tempFile = new File(getTempDir().getAbsolutePath(), PANDA_AGENT_JAR_FILE_NAME);
		try {
			boolean success = tempFile.createNewFile();
			if (!success) {
				throw new IOException("Unable to create file " + tempFile.getAbsolutePath());
			}
			os = new FileOutputStream(tempFile);
			copy(is, os);
		} catch (Exception e) {
			LOGGER.error(e);
		} finally {
			try {
				is.close();
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		return tempFile;
	}

	private static File getTempDir() {
		return new File(System.getProperty("java.io.tmpdir"));
	}

	private static void copy(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[1024 * 4];

		int n;
		while (EOF != (n = is.read(buffer))) {
			os.write(buffer, 0, n);
		}
	}

}
