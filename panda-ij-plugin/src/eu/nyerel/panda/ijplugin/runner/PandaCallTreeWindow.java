package eu.nyerel.panda.ijplugin.runner;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import eu.nyerel.panda.Constants;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
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

	public PandaCallTreeWindow() {
		refreshButton.setAction(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callTreePanel.removeAll();
				java.util.List<CallTreeNode> callTree = getCallTree();
				for (CallTreeNode node : callTree) {
					callTreePanel.add(new JLabel(node.getDescription()));
				}
			}
		});
	}

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		ContentManager cm = toolWindow.getContentManager();
		ContentFactory factory = cm.getFactory();
		Content content = factory.createContent(mainPanel, "", false);
		cm.addContent(content);
	}

	private List<CallTreeNode> getCallTree() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", Constants.RMI_PORT);
			MonitoringResultService monitoringResultService = (MonitoringResultService) registry.lookup(Constants.RMI_ID);
			return monitoringResultService.getCallTree();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
