package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.data.AgentFacade;
import eu.nyerel.panda.ijplugin.data.DumpFileReader;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;
import eu.nyerel.panda.ijplugin.runner.calltree.model.AggregatedCallTreeTableModel;
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeAggregator;
import eu.nyerel.panda.ijplugin.runner.calltree.model.CallTreeTableModel;
import eu.nyerel.panda.ijplugin.runner.calltree.model.EmptyCallTreeModel;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import javax.swing.*;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class CallTreeAction extends AnActionButton {

    public CallTreeAction(String text, Icon icon) {
        super(text, icon);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        performAction(e, getCallTreeTable());
    }

    protected abstract void performAction(AnActionEvent e, TreeTable callTreeTable);

    protected TreeTable getCallTreeTable() {
        return (TreeTable) getContextComponent();
    }

    protected void drawEmptyCallTree(final TreeTable callTreeTable) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                callTreeTable.setModel(new EmptyCallTreeModel());
                callTreeTable.setRootVisible(true);
            }
        });
    }

    protected void redrawCallTreeTable() {
        TreeTable callTreeTable = getCallTreeTable();
        List<CallTreeNode> nodes;
        if (AgentFacade.INSTANCE.isRunning()) {
            nodes = AgentFacade.INSTANCE.getCallTree();
        } else {
            nodes = DumpFileReader.INSTANCE.read();
        }
        boolean aggregate = PandaSettings.INSTANCE.isAggregateCallTree();
        if (nodes != null && !nodes.isEmpty()) {
            if (aggregate) {
                nodes = new CallTreeAggregator().aggregate(nodes);
            }
            drawCallTree(callTreeTable, nodes, aggregate);
        } else {
            drawEmptyCallTree(callTreeTable);
        }
    }

    private void drawCallTree(final TreeTable callTreeTable, final List<CallTreeNode> callTreeNodes, final boolean aggregate) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (aggregate) {
                    drawAggregatedTree(callTreeTable, callTreeNodes);
                } else {
                    drawSimpleTree(callTreeTable, callTreeNodes);
                }
            }
        });
    }

    private void drawSimpleTree(final TreeTable callTreeTable, final List<CallTreeNode> callTreeNodes) {
        CallTreeTableModel model = new CallTreeTableModel(callTreeNodes);
        callTreeTable.setModel(model);
        callTreeTable.setRootVisible(false);
        callTreeTable.getColumnModel().getColumn(0).setPreferredWidth(450);
        callTreeTable.getColumnModel().getColumn(1).setWidth(50);
    }

    private void drawAggregatedTree(final TreeTable callTreeTable, final List<CallTreeNode> callTreeNodes) {
        CallTreeTableModel model = new AggregatedCallTreeTableModel(callTreeNodes);
        callTreeTable.setModel(model);
        callTreeTable.setRootVisible(false);
        callTreeTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        callTreeTable.getColumnModel().getColumn(1).setWidth(50);
        callTreeTable.getColumnModel().getColumn(2).setWidth(50);
    }

}
