package eu.nyerel.panda.agent.monitoringresult;

import eu.nyerel.panda.Constants;
import eu.nyerel.panda.agent.util.Log;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeList;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.io.*;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum MonitoringResultDumpWriter {

    INSTANCE;

    public void clear() {
        File dumpFile = getDumpFile();
        if (dumpFile.exists()) {
            if (!dumpFile.delete()) {
                Log.warn("Failed to delete profiler dump file " + dumpFile.getAbsolutePath());
            }
        }
    }

    public void dumpCurrentResults() {
        List<CallTreeNode> callTree = MonitoringResultServiceImpl.INSTANCE.getCallTree();
        try {
            ObjectOutput out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getDumpFile())));
            out.writeObject(new CallTreeList(callTree));
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getDumpFile() {
        File file = new File(getTempDir(), Constants.INSTANCE.getDUMP_FILE_NAME());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    private File getTempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

}
