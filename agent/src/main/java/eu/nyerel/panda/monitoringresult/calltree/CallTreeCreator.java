package eu.nyerel.panda.monitoringresult.calltree;

import eu.nyerel.panda.monitoring.MethodCall;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeCreator {

	private long nextId = 0;

	public List<CallTreeNode> create(List<MethodCall> data) {
		List<CallTreeNode> callTree = new ArrayList<CallTreeNode>();
		for (MethodCall methodCall : data) {
			callTree.add(constructCallTreeNode(methodCall, methodCall.getDuration()));
		}
		return callTree;
	}

	private CallTreeNode constructCallTreeNode(MethodCall methodCall, long rootNodeDuration) {
		CallTreeNode node = new CallTreeNode();
		node.setId(nextId++);
		node.setDescription(methodCall.getDescription());
		node.setDuration(getDuration(methodCall, rootNodeDuration));
		node.setChildren(getCallTreeNodeChildren(methodCall, rootNodeDuration));
		for (CallTreeNode child : node.getChildren()) {
			child.setParent(node);
		}
		return node;
	}

	private CallTreeNodeDuration getDuration(MethodCall methodCall, long rootNodeDuration) {
		CallTreeNodeDuration duration = new CallTreeNodeDuration();
		duration.setTotal(methodCall.getDuration());
		duration.setChildren(methodCall.getChildrenDuration());
		duration.setSelf(methodCall.getSelfDuration());
		if (rootNodeDuration > 0) {
			duration.setPercentage(((double) duration.getTotal()) / rootNodeDuration);
		}
		return duration;
	}

	private ArrayList<CallTreeNode> getCallTreeNodeChildren(MethodCall methodCall, long rootNodeDuration) {
		ArrayList<CallTreeNode> children = new ArrayList<CallTreeNode>();
		for (MethodCall childMethod : methodCall.getChildren()) {
			children.add(constructCallTreeNode(childMethod, rootNodeDuration));
		}
		return children;
	}

}
