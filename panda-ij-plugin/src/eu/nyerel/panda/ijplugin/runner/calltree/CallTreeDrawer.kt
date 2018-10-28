package eu.nyerel.panda.ijplugin.runner.calltree

import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.ui.treeStructure.treetable.TreeTable
import eu.nyerel.panda.ijplugin.data.AgentFacade
import eu.nyerel.panda.ijplugin.data.DumpFileReader
import eu.nyerel.panda.ijplugin.runner.PandaSettings
import eu.nyerel.panda.ijplugin.runner.calltree.model.AggregatedCallTreeTableModel
import eu.nyerel.panda.ijplugin.runner.calltree.model.CallTreeTableModel
import eu.nyerel.panda.ijplugin.runner.calltree.model.EmptyCallTreeModel
import eu.nyerel.panda.ijplugin.runner.calltree.model.WidthAware
import eu.nyerel.panda.ijplugin.runner.calltree.support.CallTreeNodeUtils
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

import javax.swing.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object CallTreeDrawer {

    lateinit var table: TreeTable

    fun drawInBackground(project: Project?) {
        ProgressManager.getInstance().run(
                object : Task.Backgroundable(project, "Updating Call Tree", false) {
                    override fun run(indicator: ProgressIndicator) {
                        indicator.isIndeterminate = true
                        draw()
                    }
                }
        )
    }

    fun draw() {
        var nodes: List<CallTreeNode> =
                if (AgentFacade.isRunning)
                    AgentFacade.callTree
                else
                    DumpFileReader.read()

        val structured = PandaSettings.isCallTreeStructured
        val aggregate = PandaSettings.isAggregateCallTree
        if (!nodes.isEmpty()) {
            if (aggregate) {
                if (structured) {
                    nodes = CallTreeNodeUtils.aggregate(nodes)
                } else {
                    nodes = CallTreeNodeUtils.flatAggregate(nodes)
                }
            } else if (!structured) {
                nodes = CallTreeNodeUtils.flatten(nodes)
            }
            if (PandaSettings.isSortByDuration) {
                CallTreeNodeUtils.sortByDuration(nodes)
            }
            if (PandaSettings.hideProxyClasses) {
                nodes = CallTreeNodeUtils.exclude(nodes, CallTreeNodeUtils.PROXY_CLASS_FILTER)
            }
            drawNodes(nodes)
        } else {
            drawEmpty()
        }
    }

    fun drawEmpty() {
        table.setModel(EmptyCallTreeModel())
        table.setRootVisible(true)
    }

    private fun drawNodes(nodes: List<CallTreeNode>) {
        val aggregate = PandaSettings.isAggregateCallTree
        val model: CallTreeTableModel
        model = if (aggregate) {
            AggregatedCallTreeTableModel(nodes)
        } else {
            CallTreeTableModel(nodes)
        }
        SwingUtilities.invokeLater {
            table.setModel(model)
            table.setRootVisible(false)
            resizeColumns(table)
        }
    }

    private fun resizeColumns(table: TreeTable) {
        val model = table.tableModel as CallTreeTableModel
        for (i in 0 until model.columnCount) {
            val column = table.columnModel.getColumn(i)
            val info = model.columnInfos[i]
            if (info is WidthAware) {
                column.preferredWidth = (info as WidthAware).preferredWidth
            }
        }
    }

}
