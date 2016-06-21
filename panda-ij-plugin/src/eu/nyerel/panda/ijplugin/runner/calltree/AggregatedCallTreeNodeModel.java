package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.util.ui.ColumnInfo;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class AggregatedCallTreeNodeModel extends CallTreeNodeModel {

    private static final ColumnInfo[] COLUMNS = new ColumnInfo[]{
            new TreeColumnInfo("Method"),
            new TreeColumnInfo("Call Count"),
            new TreeColumnInfo("Duration")
    };

    public AggregatedCallTreeNodeModel(List<CallTreeNode> nodes) {
        super(nodes, COLUMNS);
    }

}
