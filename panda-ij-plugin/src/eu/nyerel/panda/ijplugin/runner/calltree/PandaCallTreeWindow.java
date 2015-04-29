package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.treeStructure.treetable.*;
import eu.nyerel.panda.ijplugin.runner.AgentFacade;
import eu.nyerel.panda.ijplugin.runner.calltree.renderer.DurationColumnRenderer;
import eu.nyerel.panda.ijplugin.runner.calltree.renderer.MethodColumnRenderer;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaCallTreeWindow implements ToolWindowFactory {

	private JPanel mainPanel;
	private JButton refreshButton;
	private JPanel callTreePanel;
	private TreeTable callTreeTable;

	public PandaCallTreeWindow() {
		refreshButton.setAction(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<CallTreeNode> callTreeNodes = AgentFacade.INSTANCE.getCallTree();
				if (callTreeNodes.isEmpty()) {
					callTreeTable.setModel(new EmptyCallTreeModel());
					callTreeTable.setRootVisible(true);
				} else {
					callTreeTable.setModel(new CallTreeNodeModel(callTreeNodes));
					callTreeTable.setRootVisible(false);
					TableColumnModel columnModel = callTreeTable.getColumnModel();
					columnModel.getColumn(1).setCellRenderer(new DurationColumnRenderer());
					callTreeTable.setTreeCellRenderer(new MethodColumnRenderer());

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

	private void createUIComponents() {
		callTreeTable = new TreeTable(new EmptyCallTreeModel());
		callTreeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		callTreeTable.setShowGrid(true);
		callTreeTable.getTree().setShowsRootHandles(false);
	}

}
