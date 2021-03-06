package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.ToggleActionButton
import eu.nyerel.panda.ijplugin.runner.PandaSettings
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeDrawer

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class ToggleCallTreeStructureButton : ToggleActionButton("Show as Tree", IconLoader.findIcon("/actions/showAsTree.png")) {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PandaSettings.isCallTreeStructured
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        PandaSettings.isCallTreeStructured = state
        CallTreeDrawer.drawInBackground(e.project)
    }

}
