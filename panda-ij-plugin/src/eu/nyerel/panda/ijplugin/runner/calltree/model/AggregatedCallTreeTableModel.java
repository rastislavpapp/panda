package eu.nyerel.panda.ijplugin.runner.calltree.model;

import com.intellij.util.ui.ColumnInfo;
import eu.nyerel.panda.monitoringresult.calltree.AggregatedCallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class AggregatedCallTreeTableModel extends CallTreeTableModel {

    private static final ColumnInfo[] COLUMNS = new ColumnInfo[] {
            new MethodColumnInfo(),
            new CallCountColumnInfo(),
            new DurationColumnInfo()
    };

    private static class CallCountColumnInfo extends ColumnInfo<DefaultMutableTreeNode, Integer> {

        CallCountColumnInfo() {
            super("Call Count");
        }

        @Nullable
        @Override
        public Integer valueOf(DefaultMutableTreeNode node) {
            CallTreeNodeDescriptor descriptor = (CallTreeNodeDescriptor) node.getUserObject();
            return ((AggregatedCallTreeNode) descriptor.getCallTreeNode()).getAggregateCount();
        }

    }

    public AggregatedCallTreeTableModel(List<CallTreeNode> nodes) {
        super(nodes, COLUMNS);
    }

}
