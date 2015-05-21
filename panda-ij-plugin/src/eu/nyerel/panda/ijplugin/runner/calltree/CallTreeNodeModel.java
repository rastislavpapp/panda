package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.util.ui.ColumnInfo;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeNodeModel extends ListTreeTableModel {

    private static final ColumnInfo[] COLUMNS = new ColumnInfo[]{
            new TreeColumnInfo("Method"),
            new TreeColumnInfo("Duration")
    };

    public CallTreeNodeModel(List<CallTreeNode> nodes) {
        super(createRootNode(nodes), COLUMNS);
    }

    private static TreeNode createRootNode(List<CallTreeNode> nodes) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(null);
        for (CallTreeNode node : nodes) {
            root.add(createTreeNode(node));
        }
        return root;
    }

    private static MutableTreeNode createTreeNode(CallTreeNode node) {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode(node);
        for (CallTreeNode child : node.getChildren()) {
            result.add(createTreeNode(child));
        }
        return result;
    }

    @Override
    public Object getValueAt(Object node, int column) {
        return super.getValueAt(node, column);
    }

}
