package eu.nyerel.panda.ijplugin.model

import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNodeDuration
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNodeType

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
data class AggregatedCallTreeNode(private val node: CallTreeNode) : CallTreeNode {

    override val id: Long
        get() = node.id
    override val type: CallTreeNodeType
        get() = node.type
    override var duration: CallTreeNodeDuration = node.duration
    override val description: String
        get() = node.description
    override var parent: CallTreeNode? = null
        get() = node.parent
    override var children: List<CallTreeNode> = ArrayList()
    var aggregateCount = 1

}
