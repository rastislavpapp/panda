package eu.nyerel.panda.monitoring;

import eu.nyerel.panda.util.Validate;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class MonitoredEventRecorder {

    private MonitoredEvent root = null;
    private MonitoredEvent current = null;

    public MonitoredEvent getCurrentNode() {
        return current;
    }

	public void startEvent(MonitoredEvent event) {
        Validate.notNull(event);
        if (root == null) {
            root = event;
        } else {
            Validate.notNull(current);
            current.addChildNode(event);
        }
        current = event;
    }

    public void finishCurrentEvent() {
        Validate.notNull(root);
        Validate.notNull(current);
        current.finish();
        if (current == root) {
            recordFinishedEventTree();
            root = null;
            current = null;
        } else {
            current = current.getParent();
        }
    }

    private void recordFinishedEventTree() {
		MonitoringEventListenerRegistry.fireEventCallTreeFinished(root);
    }

}
