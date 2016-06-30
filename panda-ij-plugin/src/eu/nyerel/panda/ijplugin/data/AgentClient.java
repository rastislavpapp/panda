package eu.nyerel.panda.ijplugin.data;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.StreamUtil;
import eu.nyerel.panda.Constants;

import java.io.*;
import java.net.Socket;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class AgentClient {

    private static final String HOSTNAME = "localhost";
    
    private static final Logger LOG = Logger.getInstance(DumpFileReader.class);

    public byte[] send(String message) {
        try {
            return sendInternal(message);
        } catch (IOException e) {
            throw new RuntimeException("Problem communicating with the server", e);
        }
    }

    private byte[] sendInternal(String message) throws IOException {
            try (Socket echoSocket = new Socket(HOSTNAME, Constants.RMI_PORT);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             InputStream is = echoSocket.getInputStream()) {
            out.println(message);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            StreamUtil.copyStreamContent(is, os);
            return os.toByteArray();
        }
    }

    public boolean isServerRunning() {
        try {
            String ping = new String(sendInternal("ping"), "UTF-8");
            return "pong".equals(ping.trim());
        } catch (IOException e) {
            return false;
        }
    }

}
