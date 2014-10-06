package eu.nyerel.panda.model;

import eu.nyerel.panda.util.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class CallTreeNode {

	private final long id;
	private final long start = System.currentTimeMillis();
	private boolean finished = false;
	private long duration;
	private final String description;
	private final CallTreeNode parent;
	private final List<CallTreeNode> children = new ArrayList<CallTreeNode>();

	protected CallTreeNode(long id, CallTreeNode parent, String description) {
		this.id = id;
		this.description = description;
		this.parent = parent;
	}

	public void finish() {
		Validate.isFalse(finished);
		duration = System.currentTimeMillis() - start;
		finished = true;
	}

	public long getId() {
		return id;
	}

	public long getStart() {
		return start;
	}

	public long getDuration() {
		return duration;
	}

	public long getSelfDuration() {
		return duration - getChildrenDuration();
	}

	public long getChildrenDuration() {
		long sum = 0;
		for (CallTreeNode child : children) {
			sum += child.getDuration();
		}
		return sum;
	}

	public boolean isFinished() {
		return finished;
	}

	public void addChildNode(CallTreeNode child) {
		children.add(child);
	}

	public String getDescription() {
		return description;
	}

	public CallTreeNode getParent() {
		return parent;
	}

	public List<CallTreeNode> getChildren() {
		return Collections.unmodifiableList(children);
	}

	private String asText(int level) {
		StringBuilder sb = new StringBuilder();
		indent(sb, level);
		sb.append(toString());
		if (!children.isEmpty()) {
			sb.append("\n");
		}
		for (int i = 0; i < children.size(); i++) {
			sb.append(children.get(i).asText(level + 1));
			if (i < children.size() - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	private void indent(StringBuilder sb, int level) {
		for (int i = 0; i < level; i++) {
			sb.append("    ");
		}
	}

	public String asText() {
		return asText(0);
	}

	@Override
	public String toString() {
		return description + " [" + duration + "ms]";
	}
}
