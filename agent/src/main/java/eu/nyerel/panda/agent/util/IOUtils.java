package eu.nyerel.panda.agent.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class IOUtils {

    public static void closeQuietly(Closeable output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

}
