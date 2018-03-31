package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.ToggleActionButton
import com.intellij.ui.treeStructure.treetable.TreeTable
import eu.nyerel.panda.ijplugin.runner.PandaSettings

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class ToggleShowPackageNameButton : ToggleActionButton("Show Package Name", IconLoader.findIcon("/nodes/package.png")) {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PandaSettings.isShowPackageName
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        PandaSettings.isShowPackageName = state
        DrawCallTreeAction.drawInBackground(e.project, contextComponent as TreeTable)
    }
}
