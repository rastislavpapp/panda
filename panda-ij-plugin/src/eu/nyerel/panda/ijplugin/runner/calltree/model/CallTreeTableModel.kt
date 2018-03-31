package eu.nyerel.panda.ijplugin.runner.calltree.model

import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo
import com.intellij.util.ui.ColumnInfo
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode
import javax.swing.tree.TreeNode

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
open class CallTreeTableModel 
    internal constructor(nodes: List<CallTreeNode>, 
                         private val columnInfos: Array<ColumnInfo<*, *>>) : ListTreeTableModelOnColumns(createRootNode(nodes), columnInfos) {

    internal class MethodColumnInfo : TreeColumnInfo("Method"), WidthAware {

        override val preferredWidth: Int
            get() = 400

    }

    internal class SelfDurationColumnInfo : ColumnInfo<DefaultMutableTreeNode, String>("Self Time"), WidthAware {

        override val preferredWidth: Int
            get() = 30

        override fun valueOf(node: DefaultMutableTreeNode): String? {
            val descriptor = node.userObject as CallTreeNodeDescriptor
            val duration = descriptor.callTreeNode.duration
            return duration.formatSelf()
        }

    }

    internal class TotalDurationColumnInfo : ColumnInfo<DefaultMutableTreeNode, String>("Total Time"), WidthAware {

        override val preferredWidth: Int
            get() = 30

        override fun valueOf(node: DefaultMutableTreeNode): String? {
            val descriptor = node.userObject as CallTreeNodeDescriptor
            val duration = descriptor.callTreeNode.duration
            return duration.formatTotal()
        }

    }

    constructor(nodes: List<CallTreeNode>) : this(nodes, COLUMNS) {}

    override fun getColumnInfos(): Array<ColumnInfo<*, *>> {
        return columnInfos
    }

    companion object {

        private val COLUMNS = arrayOf<ColumnInfo<*, *>>(MethodColumnInfo(), TotalDurationColumnInfo(), SelfDurationColumnInfo())

        private fun createRootNode(nodes: List<CallTreeNode>): TreeNode {
            val root = DefaultMutableTreeNode(null)
            for (node in nodes) {
                root.add(createTreeNode(node, null))
            }
            return root
        }

        private fun createTreeNode(node: CallTreeNode, parentDescriptor: CallTreeNodeDescriptor?): MutableTreeNode {
            val descriptor = CallTreeNodeDescriptor(node, parentDescriptor)

            val result = DefaultMutableTreeNode(descriptor)
            for (child in node.children) {
                result.add(createTreeNode(child, descriptor))
            }
            return result
        }
    }

}
