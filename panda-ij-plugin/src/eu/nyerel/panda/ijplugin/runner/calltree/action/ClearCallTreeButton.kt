package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.AnActionButton
import eu.nyerel.panda.ijplugin.data.AgentFacade
import eu.nyerel.panda.ijplugin.data.DumpFileReader
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeDrawer

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class ClearCallTreeButton : AnActionButton("Clear All", IconLoader.findIcon("/actions/gc.png")) {

    override fun actionPerformed(e: AnActionEvent) {
        ProgressManager.getInstance().run(
                object : Task.Backgroundable(e.project, "Clearing Call Tree", false) {
                    override fun run(indicator: ProgressIndicator) {
                        indicator.isIndeterminate = true
                        AgentFacade.clear()
                        DumpFileReader.clear()
                        CallTreeDrawer.drawEmpty()
                    }
                }
        )
    }

}
