package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ToggleActionButton;
import eu.nyerel.panda.ijplugin.runner.PandaSettings;

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
    }

}
