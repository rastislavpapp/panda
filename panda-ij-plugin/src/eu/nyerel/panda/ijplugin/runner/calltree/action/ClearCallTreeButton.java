package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.data.AgentFacade;
import eu.nyerel.panda.ijplugin.data.DumpFileReader;
import org.jetbrains.annotations.NotNull;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ClearCallTreeButton extends AnActionButton {

    public ClearCallTreeButton() {
        super("Clear All", IconLoader.findIcon("/actions/gc.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        ProgressManager.getInstance().run(
                new Task.Backgroundable(e.getProject(), "Clearing Call Tree", false) {
                    @Override
                    public void run(@NotNull ProgressIndicator indicator) {
                        indicator.setIndeterminate(true);
                        AgentFacade.INSTANCE.clear();
                        DumpFileReader.INSTANCE.clear();
                        DrawCallTreeAction.INSTANCE.drawEmpty((TreeTable) getContextComponent());
                    }
                }
        );
    }

}
