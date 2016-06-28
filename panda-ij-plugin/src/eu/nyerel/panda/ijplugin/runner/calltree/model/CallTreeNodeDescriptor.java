package eu.nyerel.panda.ijplugin.runner.calltree.model;

import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.ui.treeStructure.SimpleNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.Nullable;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeNodeDescriptor extends SimpleNode {

    private final CallTreeNode node;

    protected CallTreeNodeDescriptor(CallTreeNode node, @Nullable NodeDescriptor parentDescriptor) {
        super(null, parentDescriptor);
        this.node = node;
        getTemplatePresentation().addText(node.getDescription(), getPlainAttributes());
    }

    public CallTreeNode getCallTreeNode() {
        return node;
    }

    @Override
    public SimpleNode[] getChildren() {
        return SimpleNode.NO_CHILDREN;
    }

}
