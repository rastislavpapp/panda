package eu.nyerel.panda.ijplugin.runner

import com.intellij.execution.Executor
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader

import javax.swing.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class PandaExecutor : Executor() {

    override fun getToolWindowId(): String {
        return this.id
    }

    override fun getToolWindowIcon(): Icon {
        return AllIcons.Toolwindows.ToolWindowRun
    }

    override fun getIcon(): Icon {
        return IconLoader.getIcon("/panda-run.png", this.javaClass)
    }

    override fun getDisabledIcon(): Icon {
        return AllIcons.Process.DisabledRun
    }

    override fun getDescription(): String {
        return "Run application with Panda agent"
    }

    override fun getActionName(): String {
        return "Run with Panda"
    }

    override fun getId(): String {
        return EXECUTOR_ID
    }

    override fun getStartActionText(): String {
        return "Run with Panda"
    }

    override fun getContextActionId(): String {
        return this.id + " context-action-does-not-exist"
    }

    override fun getHelpId(): String? {
        return null
    }

    companion object {

        val EXECUTOR_ID = "Panda Executor"
    }

}
