package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.runner.AgentFacade;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ClearCallTreeButton extends RefreshCallTreeButton {

    public ClearCallTreeButton() {
        super("Clear All", IconLoader.findIcon("/actions/gc.png"));
    }

    @Override
    protected void performAction(AnActionEvent e, TreeTable callTreeTable) {
        if (AgentFacade.INSTANCE.isRunning()) {
            AgentFacade.INSTANCE.clear();
        }
        super.performAction(e, callTreeTable);
    }

}
