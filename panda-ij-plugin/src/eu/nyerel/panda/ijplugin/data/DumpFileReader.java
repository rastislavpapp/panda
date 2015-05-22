package eu.nyerel.panda.ijplugin.data;

import com.intellij.openapi.diagnostic.Logger;
import eu.nyerel.panda.Constants;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeList;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.io.*;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum DumpFileReader {

    INSTANCE;

    private static final Logger LOG = Logger.getInstance(DumpFileReader.class);

    public List<CallTreeNode> read() {
        File dumpFile = getDumpFile();
        if (dumpFile.exists()) {
            return readCallTree(dumpFile);
        } else {
            return null;
        }
    }

    private List<CallTreeNode> readCallTree(File file) {
        ObjectInput input;
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            InputStream buffer = new BufferedInputStream(is);
            input = new ObjectInputStream(buffer);
            return ((CallTreeList) input.readObject()).getNodes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.error("Error while closing input stream of file " + file, e);
                }
            }
        }
    }

    private File getDumpFile() {
        return new File(getTempDir(), Constants.DUMP_FILE_NAME);
    }

    private File getTempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }


    public void clear() {
        File dumpFile = getDumpFile();
        if (dumpFile.exists()) {
            if (!dumpFile.delete()) {
                LOG.warn("Failed to delete profiler dump file " + dumpFile.getAbsolutePath());
            }
        }
    }
}
