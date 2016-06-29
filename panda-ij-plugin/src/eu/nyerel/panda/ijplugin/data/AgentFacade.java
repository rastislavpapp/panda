package eu.nyerel.panda.ijplugin.data;

import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeList;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum AgentFacade {

    INSTANCE;

    private AgentClient agentClient = new AgentClient();

    private MonitoringResultService monitoringResultService = new MonitoringResultService() {
        @Override
        public List<CallTreeNode> getCallTree() {
            byte[] response = agentClient.send("getCallTree");
            ByteArrayInputStream is = new ByteArrayInputStream(response);
            CallTreeList callTreeList = CallTreeListReader.INSTANCE.read(is);
            return callTreeList.getNodes();
        }

        @Override
        public void clear() {
            agentClient.send("clearData");
        }

    };

    public List<CallTreeNode> getCallTree() {
        return monitoringResultService.getCallTree();
    }

    public boolean isRunning() {
        return agentClient.isServerRunning();
    }

    public void shutdown() {
        if (isRunning()) {
            agentClient.send("stop");
        }
    }

    public void clear() {
        monitoringResultService.clear();
    }

}
