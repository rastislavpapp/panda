package eu.nyerel.panda.ijplugin.runner

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.*
import com.intellij.execution.impl.DefaultJavaProgramRunner
import com.intellij.execution.process.CapturingProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import eu.nyerel.panda.ijplugin.data.AgentFacade
import eu.nyerel.panda.ijplugin.runner.calltree.PandaCallTreeWindow

import javax.swing.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class PandaProgramRunner : DefaultJavaProgramRunner() {

    private val agentFilePath: String
        get() = JarUtils.geUnpackedResourcePath("/panda-agent.jar")

    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        return try {
            (executorId == PandaExecutor.EXECUTOR_ID
                    && profile !is RunConfigurationWithSuppressedDefaultRunAction
                    && profile is RunConfigurationBase)
        } catch (ex: Exception) {
            false
        }
    }

    @Throws(ExecutionException::class)
    override fun patch(javaParameters: JavaParameters, settings: RunnerSettings?, runProfile: RunProfile, beforeExecution: Boolean) {
        super.patch(javaParameters, settings, runProfile, beforeExecution)
        if (beforeExecution) {
            val vmParametersList = javaParameters.vmParametersList
            vmParametersList.add("-javaagent:$agentFilePath")
            vmParametersList.add("-Djboss.modules.system.pkgs=eu.nyerel.panda.agent")
            val project = (runProfile as RunConfigurationBase).project
            vmParametersList.add("-Dpanda.monitored.classes=" + getMonitoredClassesString(project))
            vmParametersList.add("-Dpanda.excluded.classes=" + getExcludedClassesString(project))
        }
    }

    private fun getMonitoredClassesString(project: Project): String {
        return PandaSettings.getMonitoredClassesAsString(project)
    }

    private fun getExcludedClassesString(project: Project): String {
        return PandaSettings.getExcludedClassesAsString(project)
    }

    @Throws(ExecutionException::class)
    override fun doExecute(state: RunProfileState, environment: ExecutionEnvironment): RunContentDescriptor? {
        val descriptor = super.doExecute(state, environment)
        if (descriptor != null) {
            val processHandler = descriptor.processHandler
            processHandler?.addProcessListener(object : CapturingProcessAdapter() {

                override fun processWillTerminate(event: ProcessEvent, willBeDestroyed: Boolean) {
                    AgentFacade.shutdown()
                }

                override fun startNotified(event: ProcessEvent) {
                    showPandaToolWindow(environment.project)
                }

            })
        }
        return descriptor
    }

    override fun getRunnerId(): String {
        return RUNNER_ID
    }

    private fun showPandaToolWindow(project: Project) {
        val twm = ToolWindowManager.getInstance(project)
        val pandaToolWindow = twm.getToolWindow(PandaCallTreeWindow.PANDA_TOOL_WINDOW_ID)
        if (pandaToolWindow == null) {
            SwingUtilities.invokeLater {
                val window = twm.registerToolWindow(PandaCallTreeWindow.PANDA_TOOL_WINDOW_ID,
                        false, ToolWindowAnchor.RIGHT)
                window.show(null)
            }
        } else {
            SwingUtilities.invokeLater { pandaToolWindow.show(null) }
        }
    }

    companion object {
        const val RUNNER_ID = "Panda Runner"
    }

}
