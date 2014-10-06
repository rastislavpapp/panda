package eu.nyerel.panda.monitoring;

import eu.nyerel.panda.model.CallTreeNode;
import eu.nyerel.panda.model.MonitoringEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitoringService implements MonitoringEventListener {

	private static MonitoringService INSTANCE;

	private List<CallTreeNode> data = Collections.synchronizedList(new ArrayList<CallTreeNode>());

	private MonitoringService() {
	}

	public static MonitoringService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MonitoringService();
		}
		return INSTANCE;
	}

	public List<CallTreeNode> getMonitoringData() {
		return Collections.unmodifiableList(data);
	}

	public void clear() {
		data.clear();
	}

	@Override
	public void onCallTreeFinished(CallTreeNode node) {
		data.add(node);
	}

}
