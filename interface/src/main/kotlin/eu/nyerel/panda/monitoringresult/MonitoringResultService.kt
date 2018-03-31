package eu.nyerel.panda.monitoringresult

import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
interface MonitoringResultService {

    fun getCallTree(): List<CallTreeNode>
    fun clear()

}
