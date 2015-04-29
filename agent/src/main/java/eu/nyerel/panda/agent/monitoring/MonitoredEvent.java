package eu.nyerel.panda.agent.monitoring;

import eu.nyerel.panda.agent.util.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class MonitoredEvent {

	protected boolean finished = false;
	protected long duration;
	private final String description;
	private final MonitoredEvent parent;
	private final List<MonitoredEvent> children = new ArrayList<MonitoredEvent>();

	public MonitoredEvent(MonitoredEvent parent, String description) {
		this.description = description;
		this.parent = parent;
	}

	public void finish() {
		Validate.isFalse(finished);
		finished = true;
	}

	public long getSelfDuration() {
		return duration - getChildrenDuration();
	}

	public void addChildNode(MonitoredEvent child) {
		children.add(child);
	}

	public long getChildrenDuration() {
		long sum = 0;
		for (MonitoredEvent child : children) {
			sum += child.getDuration();
		}
		return sum;
	}

	public long getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	public MonitoredEvent getParent() {
		return parent;
	}

	public List<MonitoredEvent> getChildren() {
		return children;
	}

	public String asText() {
		return asText(0);
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

}
