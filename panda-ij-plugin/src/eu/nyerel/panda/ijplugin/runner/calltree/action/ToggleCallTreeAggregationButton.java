package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ToggleActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.data.AgentFacade;
import eu.nyerel.panda.ijplugin.data.DumpFileReader;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ToggleCallTreeAggregationButton extends ToggleActionButton {

    public ToggleCallTreeAggregationButton() {
        super("Group by Method", IconLoader.findIcon("/actions/groupByMethod.png"));
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return PandaSettings.INSTANCE.isAggregateCallTree();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        PandaSettings.INSTANCE.setAggregateCallTree(state);
        DrawCallTreeAction.INSTANCE.drawInBackground(e.getProject(),
                (TreeTable) getContextComponent());
    }

}
