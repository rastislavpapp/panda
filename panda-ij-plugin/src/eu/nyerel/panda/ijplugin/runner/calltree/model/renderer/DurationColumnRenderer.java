package eu.nyerel.panda.ijplugin.runner.calltree.model.renderer;

import eu.nyerel.panda.monitoringresult.calltree.AggregatedCallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNodeDuration;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class DurationColumnRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null && value instanceof CallTreeNode) {
            CallTreeNode callTreeNode = (CallTreeNode) value;
            return createProgressBar(callTreeNode);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    private Component createProgressBar(CallTreeNode node) {
        CallTreeNodeDuration duration = node.getDuration();
        if (node instanceof AggregatedCallTreeNode) {
            duration = duration.divide(((AggregatedCallTreeNode) node).getAggregateCount());
        }
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString(duration.getTotal() + " ms");
        progressBar.setValue(Math.round(Math.round(100 * duration.getPercentage())));
        progressBar.setToolTipText("self: " + duration.getSelf() + " ms");
        return progressBar;
    }

}
