package eu.nyerel.panda.monitoring;

import eu.nyerel.panda.model.CallTreeNode;
import eu.nyerel.panda.util.Validate;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class CallTreeRecorder {

    private CallTreeNode root = null;
    private CallTreeNode current = null;

    public CallTreeNode getCurrentNode() {
        return current;
    }

    public void startNode(CallTreeNode node) {
        Validate.notNull(node);
        if (root == null) {
            root = node;
        } else {
            Validate.notNull(current);
            current.addChildNode(node);
        }
        current = node;
    }

    public void finishCurrentNode() {
        Validate.notNull(root);
        Validate.notNull(current);
        current.finish();
        if (current == root) {
            recordFinishedCallTree();
            root = null;
            current = null;
        } else {
            current = current.getParent();
        }
    }

    private void recordFinishedCallTree() {
		MonitoringEventListenerRegistry.fireCallTreeFinished(root);
    }

}
