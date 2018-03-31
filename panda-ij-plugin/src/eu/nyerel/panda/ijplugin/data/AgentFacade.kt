package eu.nyerel.panda.ijplugin.data

import eu.nyerel.dolphin.core.client.DolphinObjectFactory
import eu.nyerel.dolphin.core.server.DolphinManagementInterface
import eu.nyerel.panda.monitoringresult.MonitoringResultService
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

import java.net.ConnectException

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object AgentFacade {

    private val clientFactory = DolphinObjectFactory()

    private val monitoringResultService = clientFactory.create(MonitoringResultService::class.java)
    private val management = clientFactory.create(DolphinManagementInterface::class.java)

    val callTree: List<CallTreeNode>
        get() = monitoringResultService.getCallTree()

    val isRunning: Boolean
        get() {
            try {
                return management.isRunning
            } catch (e: Exception) {
                if (e.cause != null && e.cause is ConnectException) {
                    return false
                }
                throw e
            }
        }

    fun shutdown() {
        if (isRunning) {
            management.stop()
        }
    }

    fun clear() {
        monitoringResultService.clear()
    }

}
