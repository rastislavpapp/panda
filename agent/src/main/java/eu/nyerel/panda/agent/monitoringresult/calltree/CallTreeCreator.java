package eu.nyerel.panda.agent.monitoringresult.calltree;

import eu.nyerel.panda.agent.monitoring.MethodCall;
import eu.nyerel.panda.agent.monitoring.MonitoredEvent;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNodeDuration;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeCreator {

	private long nextId = 0;

	public List<CallTreeNode> create(List<MonitoredEvent> data) {
		List<CallTreeNode> callTree = new ArrayList<CallTreeNode>();
		for (MonitoredEvent event : data) {
			callTree.add(constructCallTreeNode(event, event.getDuration()));
		}
		return callTree;
	}

	private CallTreeNode constructCallTreeNode(MonitoredEvent event, long rootNodeDuration) {
		CallTreeNode node = new CallTreeNode();
		node.setId(nextId++);
		node.setType(determineType(event));
		node.setDescription(event.getDescription());
		node.setDuration(getDuration(event, rootNodeDuration));
		node.setChildren(getCallTreeNodeChildren(event, rootNodeDuration));
		for (CallTreeNode child : node.getChildren()) {
			child.setParent(node);
		}
		return node;
	}

	private CallTreeNodeType determineType(MonitoredEvent event) {
		if (event instanceof MethodCall) {
			return CallTreeNodeType.METHOD;
		} else {
			return CallTreeNodeType.SQL;
		}
	}

	private CallTreeNodeDuration getDuration(MonitoredEvent event, long rootNodeDuration) {
		CallTreeNodeDuration duration = new CallTreeNodeDuration();
		duration.setTotal(event.getDuration());
		duration.setChildren(event.getChildrenDuration());
		duration.setSelf(event.getSelfDuration());
		if (rootNodeDuration > 0) {
			duration.setPercentage(((double) duration.getTotal()) / rootNodeDuration);
		} else {
			duration.setPercentage(1);
		}
		return duration;
	}

	private ArrayList<CallTreeNode> getCallTreeNodeChildren(MonitoredEvent event, long rootNodeDuration) {
		ArrayList<CallTreeNode> children = new ArrayList<CallTreeNode>();
		for (MonitoredEvent childEvent : event.getChildren()) {
			children.add(constructCallTreeNode(childEvent, rootNodeDuration));
		}
		return children;
	}

}
