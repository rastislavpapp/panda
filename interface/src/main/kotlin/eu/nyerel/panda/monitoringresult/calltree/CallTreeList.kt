package eu.nyerel.panda.monitoringresult.calltree

import java.io.Serializable

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
data class CallTreeList(val nodes: List<CallTreeNode>) : Serializable