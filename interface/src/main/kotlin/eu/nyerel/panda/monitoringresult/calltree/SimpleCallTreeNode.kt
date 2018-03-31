package eu.nyerel.panda.monitoringresult.calltree

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
data class SimpleCallTreeNode(override val id: Long,
                              override val type: CallTreeNodeType,
                              override val duration: CallTreeNodeDuration,
                              override val description: String,
                              override var parent: CallTreeNode?,
                              override val children: List<CallTreeNode>) : CallTreeNode {

    override fun toString(): String {
        return "$description($duration)"
    }

}
