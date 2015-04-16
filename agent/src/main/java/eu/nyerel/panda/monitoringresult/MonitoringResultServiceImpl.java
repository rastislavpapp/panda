package eu.nyerel.panda.monitoringresult;

import eu.nyerel.panda.monitoring.MonitoredEvent;
import eu.nyerel.panda.monitoring.MonitoringEventListener;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeCreator;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitoringResultServiceImpl implements MonitoringResultService, MonitoringEventListener {

	private static MonitoringResultServiceImpl INSTANCE;

	private List<MonitoredEvent> data = Collections.synchronizedList(new ArrayList<MonitoredEvent>());

	private MonitoringResultServiceImpl() {
		super();
	}

	public static MonitoringResultServiceImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MonitoringResultServiceImpl();
		}
		return INSTANCE;
	}

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