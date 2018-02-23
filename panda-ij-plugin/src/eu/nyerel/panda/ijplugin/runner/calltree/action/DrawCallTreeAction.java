package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.util.ui.ColumnInfo;
import eu.nyerel.panda.ijplugin.data.AgentFacade;
import eu.nyerel.panda.ijplugin.data.DumpFileReader;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;
import eu.nyerel.panda.ijplugin.runner.calltree.model.AggregatedCallTreeTableModel;
import eu.nyerel.panda.ijplugin.runner.calltree.model.CallTreeTableModel;
import eu.nyerel.panda.ijplugin.runner.calltree.model.EmptyCallTreeModel;
import eu.nyerel.panda.ijplugin.runner.calltree.model.WidthAware;
import eu.nyerel.panda.ijplugin.runner.calltree.support.CallTreeNodeUtils;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum DrawCallTreeAction {

    INSTANCE;

    public void drawInBackground(Project project, final TreeTable table) {
        ProgressManager.getInstance().run(
                new Task.Backgroundable(project, "Updating Call Tree", false) {
                    @Override
                    public void run(@NotNull ProgressIndicator indicator) {
                        indicator.setIndeterminate(true);
                        DrawCallTreeAction.INSTANCE.draw(table);
                    }
                }
        );
    }

    public void draw(TreeTable table) {
        List<CallTreeNode> nodes;
        if (AgentFacade.INSTANCE.isRunning()) {
            nodes = AgentFacade.INSTANCE.getCallTree();
        } else {
            nodes = DumpFileReader.INSTANCE.read();
        }
        boolean structured = PandaSettings.INSTANCE.isCallTreeStructured();
        boolean aggregate = PandaSettings.INSTANCE.isAggregateCallTree();
        if (nodes != null && !nodes.isEmpty()) {
            if (aggregate) {
                if (structured) {
                    nodes = CallTreeNodeUtils.aggregate(nodes);
                } else {
                    nodes = CallTreeNodeUtils.flatAggregate(nodes);
                }
            } else if (!structured) {
                nodes = CallTreeNodeUtils.flatten(nodes);
            }
            if (PandaSettings.INSTANCE.isSortByDuration()) {
                nodes = CallTreeNodeUtils.sortByDuration(nodes);
            }
            drawNodes(table, nodes);
        } else {
            drawEmpty(table);
        }
    }

    public void drawEmpty(final TreeTable table) {
        table.setModel(new EmptyCallTreeModel());
        table.setRootVisible(true);
    }

    private void drawNodes(final TreeTable table, final List<CallTreeNode> nodes) {
        boolean aggregate = PandaSettings.INSTANCE.isAggregateCallTree();
        final CallTreeTableModel model;
        if (aggregate) {
            model = new AggregatedCallTreeTableModel(nodes);
        } else {
            model = new CallTreeTableModel(nodes);
        }
        SwingUtilities.invokeLater(() -> {
            table.setModel(model);
            table.setRootVisible(false);
            resizeColumns(table);
        });
    }

    private void resizeColumns(final TreeTable table) {
        CallTreeTableModel model = (CallTreeTableModel) table.getTableModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            ColumnInfo info = model.getColumnInfos()[i];
            if (info instanceof WidthAware) {
                column.setPreferredWidth(((WidthAware) info).getPreferredWidth());
            }
        }
    }

}
