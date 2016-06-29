package eu.nyerel.panda.agent.monitoringresult;

import eu.nyerel.panda.agent.monitoring.MonitoredEvent;
import eu.nyerel.panda.agent.monitoring.MonitoringEventListener;
import eu.nyerel.panda.agent.monitoringresult.calltree.CallTreeCreator;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum MonitoringResultServiceImpl implements MonitoringResultService, MonitoringEventListener {

    INSTANCE;

    private List<MonitoredEvent> data = Collections.synchronizedList(new ArrayList<MonitoredEvent>());

    public List<CallTreeNode> getCallTree() {
        return constructCallTree(data);
    }

    private List<CallTreeNode> constructCallTree(List<MonitoredEvent> data) {
        return new CallTreeCreator().create(data);
    }

    public void clear() {
        data.clear();
    }

    @Override
    public void onCallTreeFinished(MonitoredEvent node) {
        data.add(node);
    }

}