package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import org.jetbrains.annotations.NotNull;

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
                new Task.Backgroundable(evt.getProject(), "Updating Call Tree", false) {
                    @Override
                    public void run(@NotNull ProgressIndicator indicator) {
                        indicator.setIndeterminate(true);
                        redrawCallTreeTable();
                    }
                }
        );
    }

}
