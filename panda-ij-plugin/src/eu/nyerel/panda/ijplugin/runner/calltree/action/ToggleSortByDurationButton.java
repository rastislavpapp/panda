package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ToggleActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ToggleSortByDurationButton extends ToggleActionButton {

    public ToggleSortByDurationButton() {
        super("Sort by Duration", IconLoader.findIcon("/actions/sortDesc.png"));
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return PandaSettings.INSTANCE.isSortByDuration();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        PandaSettings.INSTANCE.setSortByDuration(state);
        DrawCallTreeAction.INSTANCE.drawInBackground(e.getProject(),
                (TreeTable) getContextComponent());
    }


}
