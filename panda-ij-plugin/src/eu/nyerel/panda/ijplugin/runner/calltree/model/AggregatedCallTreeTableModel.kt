package eu.nyerel.panda.ijplugin.runner.calltree.model

import com.intellij.util.ui.ColumnInfo
import eu.nyerel.panda.ijplugin.model.AggregatedCallTreeNode
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

import javax.swing.tree.DefaultMutableTreeNode

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class AggregatedCallTreeTableModel(nodes: List<CallTreeNode>) : CallTreeTableModel(nodes, COLUMNS) {

    private class CallCountColumnInfo internal constructor() : ColumnInfo<DefaultMutableTreeNode, Int>("Invocations"), WidthAware {

        override val preferredWidth: Int
            get() = 30

        override fun valueOf(node: DefaultMutableTreeNode): Int? {
            val descriptor = node.userObject as CallTreeNodeDescriptor
            return (descriptor.callTreeNode as AggregatedCallTreeNode).aggregateCount
        }

    }

    companion object {

        private val COLUMNS = arrayOf<ColumnInfo<*, *>>(CallTreeTableModel.MethodColumnInfo(), CallCountColumnInfo(), CallTreeTableModel.TotalDurationColumnInfo(), CallTreeTableModel.SelfDurationColumnInfo())
    }

}
