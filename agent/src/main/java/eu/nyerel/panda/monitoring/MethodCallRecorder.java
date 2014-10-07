package eu.nyerel.panda.monitoring;

import eu.nyerel.panda.util.Validate;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class MethodCallRecorder {

    private MethodCall root = null;
    private MethodCall current = null;

    public MethodCall getCurrentNode() {
        return current;
    }

    public void startMethod(MethodCall node) {
        Validate.notNull(node);
        if (root == null) {
            root = node;
        } else {
            Validate.notNull(current);
            current.addChildNode(node);
        }
        current = node;
    }

    public void finishCurrentMethod() {
        Validate.notNull(root);
        Validate.notNull(current);
        current.finish();
        if (current == root) {
            recordFinishedMethodTree();
            root = null;
            current = null;
        } else {
            current = current.getParent();
        }
    }

    private void recordFinishedMethodTree() {
		MonitoringEventListenerRegistry.fireMethodCallTreeFinished(root);
    }

}
