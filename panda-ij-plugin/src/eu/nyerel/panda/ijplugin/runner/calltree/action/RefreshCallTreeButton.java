package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class RefreshCallTreeButton extends AnActionButton {

    public RefreshCallTreeButton() {
        super("Refresh", IconLoader.findIcon("/actions/refresh.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DrawCallTreeAction.INSTANCE.drawInBackground(e.getProject(),
                (TreeTable) getContextComponent());
    }

}
