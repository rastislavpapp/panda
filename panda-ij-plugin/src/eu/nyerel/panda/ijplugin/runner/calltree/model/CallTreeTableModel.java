package eu.nyerel.panda.ijplugin.runner.calltree.model;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.ide.util.treeView.PresentableNodeDescriptor;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns;
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.util.ui.ColumnInfo;
import eu.nyerel.panda.ijplugin.runner.calltree.model.renderer.DurationColumnRenderer;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.Nullable;

import javax.swing.table.TableCellRenderer;
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
            new DurationColumnInfo()
    };
    private final ColumnInfo[] columnInfos;

    static class MethodColumnInfo extends TreeColumnInfo {
        MethodColumnInfo() {
            super("Method");
        }
    }

    static class DurationColumnInfo extends ColumnInfo<DefaultMutableTreeNode, CallTreeNode> {

        static final DurationColumnRenderer RENDERER = new DurationColumnRenderer();

        DurationColumnInfo() {
            super("Duration");
        }

        @Nullable
        @Override
        public TableCellRenderer getRenderer(DefaultMutableTreeNode node) {
            return RENDERER;
        }

        @Nullable
        @Override
        public CallTreeNode valueOf(DefaultMutableTreeNode node) {
            CallTreeNodeDescriptor descriptor = (CallTreeNodeDescriptor) node.getUserObject();
            return descriptor.getCallTreeNode();
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
