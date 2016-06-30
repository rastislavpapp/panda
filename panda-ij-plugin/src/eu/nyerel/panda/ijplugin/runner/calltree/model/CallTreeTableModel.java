package eu.nyerel.panda.ijplugin.runner.calltree.model;

import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns;
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.util.ui.ColumnInfo;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNodeDuration;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeTableModel extends ListTreeTableModelOnColumns {

    private static final ColumnInfo[] COLUMNS = new ColumnInfo[] {
            new MethodColumnInfo(),
            new TotalDurationColumnInfo(),
            new SelfDurationColumnInfo()
    };
    private final ColumnInfo[] columnInfos;

    static class MethodColumnInfo extends TreeColumnInfo implements WidthAware {
        MethodColumnInfo() {
            super("Method");
        }

        @Override
        public int getPreferredWidth() {
            return 400;
        }

    }

    static class SelfDurationColumnInfo extends ColumnInfo<DefaultMutableTreeNode, String>
        implements WidthAware {

        public SelfDurationColumnInfo() {
            super("Self Time");
        }

        @Nullable
        @Override
        public String valueOf(DefaultMutableTreeNode node) {
            CallTreeNodeDescriptor descriptor = (CallTreeNodeDescriptor) node.getUserObject();
            CallTreeNodeDuration duration = descriptor.getCallTreeNode().getDuration();
            return duration.formatSelf();
        }

        @Override
        public int getPreferredWidth() {
            return 30;
        }

    }

    static class TotalDurationColumnInfo extends ColumnInfo<DefaultMutableTreeNode, String>
            implements WidthAware {

        TotalDurationColumnInfo() {
            super("Total Time");
        }

        @Nullable
        @Override
        public String valueOf(DefaultMutableTreeNode node) {
            CallTreeNodeDescriptor descriptor = (CallTreeNodeDescriptor) node.getUserObject();
            CallTreeNodeDuration duration = descriptor.getCallTreeNode().getDuration();
            return duration.formatTotal();
        }

        @Override
        public int getPreferredWidth() {
            return 30;
        }

    }

    public CallTreeTableModel(List<CallTreeNode> nodes) {
        this(nodes, COLUMNS);
    }

    CallTreeTableModel(List<CallTreeNode> nodes, ColumnInfo[] columns) {
        super(createRootNode(nodes), columns);
        this.columnInfos = columns;
    }

    public ColumnInfo[] getColumnInfos() {
        return columnInfos;
    }

    private static TreeNode createRootNode(List<CallTreeNode> nodes) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(null);
        for (CallTreeNode node : nodes) {
            root.add(createTreeNode(node, null));
        }
        return root;
    }

    private static MutableTreeNode createTreeNode(CallTreeNode node, CallTreeNodeDescriptor parentDescriptor) {
        CallTreeNodeDescriptor descriptor = new CallTreeNodeDescriptor(node, parentDescriptor);

        DefaultMutableTreeNode result = new DefaultMutableTreeNode(descriptor);
        for (CallTreeNode child : node.getChildren()) {
            result.add(createTreeNode(child, descriptor));
        }
        return result;
    }

}
