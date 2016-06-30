package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ToggleActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ToggleShowPackageNameButton extends ToggleActionButton {

    public ToggleShowPackageNameButton() {
        super("Show Package Name", IconLoader.findIcon("/nodes/package.png"));
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return PandaSettings.INSTANCE.isShowPackageName();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        PandaSettings.INSTANCE.setShowPackageName(state);
        DrawCallTreeAction.INSTANCE.drawInBackground(e.getProject(),
                (TreeTable) getContextComponent());
    }
}
