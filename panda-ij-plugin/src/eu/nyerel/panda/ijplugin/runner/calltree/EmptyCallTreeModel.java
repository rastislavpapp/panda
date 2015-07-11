package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.util.ui.ColumnInfo;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class EmptyCallTreeModel extends ListTreeTableModel {

    private static final ColumnInfo[] COLUMNS = new ColumnInfo[]{
            new TreeColumnInfo("")
    };

    public EmptyCallTreeModel() {
        super(new DefaultMutableTreeNode("No monitoring data to display (hit 'Refresh' button)"), COLUMNS);
    }

}
