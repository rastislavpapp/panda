package eu.nyerel.panda.monitoringresult.calltree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeNode {

	private long id;
	private CallTreeNodeDuration duration;
	private String description;
	private CallTreeNode parent;
	private List<CallTreeNode> children = new ArrayList<CallTreeNode>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CallTreeNodeDuration getDuration() {
		return duration;
	}

	public void setDuration(CallTreeNodeDuration duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CallTreeNode getParent() {
		return parent;
	}

	public void setParent(CallTreeNode parent) {
		this.parent = parent;
	}

	public List<CallTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<CallTreeNode> children) {
		this.children = children;
	}
}
