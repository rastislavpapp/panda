package eu.nyerel.panda.monitoringresult;

import eu.nyerel.panda.monitoring.MethodCall;
import eu.nyerel.panda.monitoring.MonitoringEventListener;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeCreator;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import eu.nyerel.panda.util.IdSequenceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitoringResultService implements MonitoringEventListener {

	private static MonitoringResultService INSTANCE;

	private List<MethodCall> data = Collections.synchronizedList(new ArrayList<MethodCall>());

	private MonitoringResultService() {
	}

	public static MonitoringResultService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MonitoringResultService();
		}
		return INSTANCE;
	}

	public List<CallTreeNode> getCallTree() {
		return constructCallTree(data);
	}

	private List<CallTreeNode> constructCallTree(List<MethodCall> data) {
		return new CallTreeCreator().create(data);
	}

	public void clear() {
		data.clear();
	}

	@Override
	public void onCallTreeFinished(MethodCall node) {
		data.add(node);
	}

}
