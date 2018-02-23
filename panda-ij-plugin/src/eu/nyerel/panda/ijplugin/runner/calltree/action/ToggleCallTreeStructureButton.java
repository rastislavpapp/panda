package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ToggleActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ToggleCallTreeStructureButton extends ToggleActionButton {

    public ToggleCallTreeStructureButton() {
        super("Show as Tree", IconLoader.findIcon("/actions/showAsTree.png"));
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return PandaSettings.INSTANCE.isCallTreeStructured();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        PandaSettings.INSTANCE.setCallTreeStructured(state);
        DrawCallTreeAction.INSTANCE.drawInBackground(e.getProject(),
                (TreeTable) getContextComponent());
    }

}
