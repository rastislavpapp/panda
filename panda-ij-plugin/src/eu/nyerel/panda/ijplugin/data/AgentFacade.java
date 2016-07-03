package eu.nyerel.panda.ijplugin.data;

import eu.nyerel.dolphin.core.client.DolphinObjectFactory;
import eu.nyerel.dolphin.core.server.DolphinManagementInterface;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.net.ConnectException;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum AgentFacade {

    INSTANCE;

    private DolphinObjectFactory clientFactory = new DolphinObjectFactory();

    private MonitoringResultService monitoringResultService = clientFactory.create(MonitoringResultService.class);
    private DolphinManagementInterface management = clientFactory.create(DolphinManagementInterface.class);

    public List<CallTreeNode> getCallTree() {
        return monitoringResultService.getCallTree();
    }

    public boolean isRunning() {
        try {
            return management.isRunning();
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass().equals(ConnectException.class)) {
                return false;
            }
            throw e;
        }
    }

    public void shutdown() {
        if (isRunning()) {
            management.stop();
        }
    }

    public void clear() {
        monitoringResultService.clear();
    }

}
