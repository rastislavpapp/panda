package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import eu.nyerel.panda.monitoringresult.calltree.AggregatedCallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class AggregatedCallTreeTable extends CallTreeTable {

    public AggregatedCallTreeTable(TreeTableModel treeTableModel) {
        super(treeTableModel);
    }

    @Override
    public Object getValueAt(int row, int column) {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) super.getValueAt(row, column);
        Object object = treeNode.getUserObject();
        if (object instanceof CallTreeNode) {
            AggregatedCallTreeNode node = (AggregatedCallTreeNode) object;
            if (column == 1) {
                return node.getAggregateCount();
            } else {
                return super.getValueAt(row, column);
            }
        } else {
            return super.getValueAt(row, column);
        }
    }

}
