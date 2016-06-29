package eu.nyerel.panda.monitoringresult;

import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public interface MonitoringResultService {

    List<CallTreeNode> getCallTree();
    void clear();

}
