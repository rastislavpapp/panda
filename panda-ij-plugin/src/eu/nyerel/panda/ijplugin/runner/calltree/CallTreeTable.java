package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import eu.nyerel.panda.ijplugin.runner.calltree.renderer.DurationColumnRenderer;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeTable extends TreeTable {

    public CallTreeTable(TreeTableModel treeTableModel) {
        super(treeTableModel);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (column == 1) {
            return new DurationColumnRenderer();
        } else {
            return super.getCellRenderer(row, column);
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) super.getValueAt(row, column);
        Object object = treeNode.getUserObject();
        if (object instanceof CallTreeNode) {
            CallTreeNode node = (CallTreeNode) object;
            switch (column) {
                case 0: return node.getDescription();
                default: return node;
            }
        } else {
            return super.getValueAt(row, column);
        }
    }

}
