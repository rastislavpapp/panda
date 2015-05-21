package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaCallTreeWindow implements ToolWindowFactory {

    public static final String PANDA_TOOL_WINDOW_ID = "Panda";

    private JPanel mainPanel;
    private JPanel callTreePanel;
    private TreeTable callTreeTable;

    public PandaCallTreeWindow() {

    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager cm = toolWindow.getContentManager();
        ContentFactory factory = cm.getFactory();
        Content content = factory.createContent(mainPanel, "", false);
        cm.addContent(content);
        registerToolbar();
    }

    private void registerToolbar() {
        ActionManager actionManager = ActionManager.getInstance();
        ActionGroup actionGroup = (ActionGroup) actionManager.getAction("CallTreeToolWindowGroup");

        ActionToolbar toolbar = actionManager.createActionToolbar("", actionGroup, true);
        java.util.List<AnAction> actions = toolbar.getActions(true);
        for (AnAction action : actions) {
            if (action instanceof AnActionButton) {
                ((AnActionButton) action).setContextComponent(callTreeTable);
            }
        }

        toolbar.setTargetComponent(mainPanel);
        mainPanel.add(toolbar.getComponent(), BorderLayout.NORTH);
    }

    private void createUIComponents() {
        callTreeTable = new TreeTable(new EmptyCallTreeModel());
    }

}
