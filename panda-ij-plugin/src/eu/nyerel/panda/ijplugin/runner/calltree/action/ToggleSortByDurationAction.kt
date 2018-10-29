package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import eu.nyerel.panda.ijplugin.runner.PandaSettings
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeDrawer

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class ToggleSortByDurationAction : ToggleAction("Sort by Duration") {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PandaSettings.isSortByDuration
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        PandaSettings.isSortByDuration = state
        CallTreeDrawer.drawInBackground(e.project)
    }

}
