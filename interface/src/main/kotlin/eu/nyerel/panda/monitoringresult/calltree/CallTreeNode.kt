package eu.nyerel.panda.monitoringresult.calltree

import java.io.Serializable

interface CallTreeNode : Serializable {
    val id: Long
    val type: CallTreeNodeType
    val duration: CallTreeNodeDuration
    val description: String
    var parent: CallTreeNode?
    var children: List<CallTreeNode>
}