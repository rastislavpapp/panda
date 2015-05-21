package eu.nyerel.panda.ijplugin.runner.calltree.renderer;

import com.intellij.ide.util.treeView.NodeRenderer;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodColumnRenderer extends NodeRenderer {

    @Override
    public void customizeCellRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
        CallTreeNode callNode = (CallTreeNode) treeNode.getUserObject();
        String label = callNode.getDescription();
        super.customizeCellRenderer(tree, label, selected, expanded, leaf, row, hasFocus);
    }

}
