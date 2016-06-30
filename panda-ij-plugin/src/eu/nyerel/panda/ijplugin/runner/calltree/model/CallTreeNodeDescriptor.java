package eu.nyerel.panda.ijplugin.runner.calltree.model;

import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.ui.treeStructure.SimpleNode;
import eu.nyerel.panda.ijplugin.model.MethodDescriptionParser;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;
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
        getTemplatePresentation().addText(getDescription(node), getPlainAttributes());
    }

    private String getDescription(CallTreeNode node) {
        if (PandaSettings.INSTANCE.isShowPackageName()) {
            return node.getDescription();
        } else {
            return MethodDescriptionParser.INSTANCE.parse(node.getDescription())
                    .getDescriptionWithoutPackageName();
        }
    }

    public CallTreeNode getCallTreeNode() {
        return node;
    }

    @Override
    public SimpleNode[] getChildren() {
        return SimpleNode.NO_CHILDREN;
    }

}
