package eu.nyerel.panda.ijplugin.data;

import eu.nyerel.panda.monitoringresult.calltree.CallTreeList;

import java.io.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum CallTreeListReader {

    INSTANCE;

    public CallTreeList read(InputStream is) {
        ObjectInput input;
        try {
            InputStream buffer = new BufferedInputStream(is);
            input = new ObjectInputStream(buffer);
            return ((CallTreeList) input.readObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
