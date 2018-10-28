package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import eu.nyerel.panda.ijplugin.runner.PandaSettings
import eu.nyerel.panda.ijplugin.runner.calltree.CallTreeDrawer

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class ToggleHideProxyClassesAction: ToggleAction("Hide proxy classes") {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PandaSettings.hideProxyClasses
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        PandaSettings.hideProxyClasses = state
        CallTreeDrawer.drawInBackground(e.project)
    }

}