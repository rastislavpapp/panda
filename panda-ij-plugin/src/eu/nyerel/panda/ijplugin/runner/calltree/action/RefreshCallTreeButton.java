package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.runner.AgentFacade;
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeNodeModel;
import eu.nyerel.panda.ijplugin.runner.calltree.renderer.DurationColumnRenderer;
import eu.nyerel.panda.ijplugin.runner.calltree.renderer.MethodColumnRenderer;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class RefreshCallTreeButton extends CallTreeAction {

    public RefreshCallTreeButton() {
        super("Refresh", IconLoader.findIcon("/actions/refresh.png"));
    }

    @Override
    protected void performAction(AnActionEvent evt, final TreeTable callTreeTable) {
        ProgressManager.getInstance().run(
                new Task.Backgroundable(evt.getProject(), "Updating call tree", false) {
                    @Override
                    public void run(@NotNull ProgressIndicator indicator) {
                        indicator.setIndeterminate(true);
                        if (AgentFacade.INSTANCE.isRunning()) {
                            List<CallTreeNode> callTreeNodes = AgentFacade.INSTANCE.getCallTree();
                            if (callTreeNodes.isEmpty()) {
                                drawEmptyCallTree(callTreeTable);
                            } else {
                                drawCallTree(callTreeTable, callTreeNodes);
                            }
                        }
                    }
                }
        );
    }

    private void drawCallTree(final TreeTable callTreeTable, final List<CallTreeNode> callTreeNodes) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                callTreeTable.setModel(new CallTreeNodeModel(callTreeNodes));
                callTreeTable.setRootVisible(false);
                TableColumnModel columnModel = callTreeTable.getColumnModel();
                columnModel.getColumn(1).setCellRenderer(new DurationColumnRenderer());
                callTreeTable.setTreeCellRenderer(new MethodColumnRenderer());
                callTreeTable.getColumnModel().getColumn(0).setPreferredWidth(450);
                callTreeTable.getColumnModel().getColumn(1).setWidth(50);
            }
        });
    }

}
