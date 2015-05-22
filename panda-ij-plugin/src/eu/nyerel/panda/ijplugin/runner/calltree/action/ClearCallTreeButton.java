package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.runner.AgentFacade;
import org.jetbrains.annotations.NotNull;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ClearCallTreeButton extends CallTreeAction {

    public ClearCallTreeButton() {
        super("Clear All", IconLoader.findIcon("/actions/gc.png"));
    }

    @Override
    protected void performAction(AnActionEvent evt, final TreeTable callTreeTable) {
        ProgressManager.getInstance().run(
                new Task.Backgroundable(evt.getProject(), "Clearing call tree", false) {
                    @Override
                    public void run(@NotNull ProgressIndicator indicator) {
                        indicator.setIndeterminate(true);
                        AgentFacade.INSTANCE.clear();
                        drawEmptyCallTree(callTreeTable);
                    }
                }
        );
    }

}
