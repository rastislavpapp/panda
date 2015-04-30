package eu.nyerel.panda.ijplugin.runner.calltree.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import eu.nyerel.panda.ijplugin.runner.AgentFacade;
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeNodeModel;
import eu.nyerel.panda.ijplugin.runner.calltree.EmptyCallTreeModel;
import eu.nyerel.panda.ijplugin.runner.calltree.renderer.DurationColumnRenderer;
import eu.nyerel.panda.ijplugin.runner.calltree.renderer.MethodColumnRenderer;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class RefreshCallTreeButton extends CallTreeAction {

	public RefreshCallTreeButton() {
		super("Refresh", IconLoader.findIcon("/actions/refresh.png"));
	}

	public RefreshCallTreeButton(String text, Icon icon) {
		super(text, icon);
	}

	@Override
	protected void performAction(AnActionEvent e, TreeTable callTreeTable) {
		if (AgentFacade.INSTANCE.isRunning()) {
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
				callTreeTable.getColumnModel().getColumn(0).setPreferredWidth(450);
				callTreeTable.getColumnModel().getColumn(1).setWidth(50);
			}
		} else {
			setEnabled(false);
			updateButton(e);
		}
	}

	@Override
	public boolean isEnabled() {
		return AgentFacade.INSTANCE.isRunning();
	}

}
