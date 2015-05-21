package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.treeStructure.treetable.TreeTable;

import javax.swing.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class CallTreeAction extends AnActionButton {

    public CallTreeAction(String text, Icon icon) {
        super(text, icon);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        performAction(e, getCallTreeTable());
    }

    protected abstract void performAction(AnActionEvent e, TreeTable callTreeTable);

    protected TreeTable getCallTreeTable() {
        return (TreeTable) getContextComponent();
    }

}
