package eu.nyerel.panda.ijplugin.runner.calltree.model

import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo
import com.intellij.util.ui.ColumnInfo

import javax.swing.tree.DefaultMutableTreeNode

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class EmptyCallTreeModel : ListTreeTableModelOnColumns(DefaultMutableTreeNode("No monitoring data to display (hit 'Refresh' button)"), COLUMNS) {
    companion object {

        private val COLUMNS = arrayOf<ColumnInfo<*, *>>(TreeColumnInfo(""))
    }

}
