package eu.nyerel.panda.ijplugin.runner.calltree.model;

import com.intellij.ui.dualView.TreeTableView;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeTable extends TreeTableView { //TODO: delete

    public CallTreeTable(ListTreeTableModelOnColumns treeTableModel) {
        super(treeTableModel);
    }


}
