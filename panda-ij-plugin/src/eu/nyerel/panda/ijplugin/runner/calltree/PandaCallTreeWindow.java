package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import eu.nyerel.panda.Constants;
import eu.nyerel.panda.ijplugin.runner.AgentFacade;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaCallTreeWindow implements ToolWindowFactory {

	private JPanel mainPanel;
	private JButton refreshButton;
	private JPanel callTreePanel;
	private JTree callTree;

	public PandaCallTreeWindow() {
		refreshButton.setAction(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callTree.removeAll();
				DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
				callTree.setModel(new DefaultTreeModel(rootNode));
				callTree.setCellRenderer(new CellTreeNodeRenderer());
				for (CallTreeNode node : AgentFacade.INSTANCE.getCallTree()) {
					rootNode.add(createCallTreeNodeUI(node));
				}
			}
		});
	}

	private MutableTreeNode createCallTreeNodeUI(CallTreeNode node) {
		DefaultMutableTreeNode uiNode = new DefaultMutableTreeNode(node.getDescription());
		for (CallTreeNode child : node.getChildren()) {
			uiNode.add(createCallTreeNodeUI(child));
		}
		return uiNode;
	}

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		ContentManager cm = toolWindow.getContentManager();
		ContentFactory factory = cm.getFactory();
		Content content = factory.createContent(mainPanel, "", false);
		cm.addContent(content);
	}

}
