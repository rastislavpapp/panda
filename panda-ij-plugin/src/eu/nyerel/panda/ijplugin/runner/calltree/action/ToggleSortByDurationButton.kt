package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.ToggleActionButton
import eu.nyerel.panda.ijplugin.runner.PandaSettings
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeDrawer

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class ToggleSortByDurationButton : ToggleActionButton("Sort by Duration", IconLoader.findIcon("/actions/sortDesc.png")) {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PandaSettings.isSortByDuration
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        PandaSettings.isSortByDuration = state
        CallTreeDrawer.drawInBackground(e.project)
    }

}
