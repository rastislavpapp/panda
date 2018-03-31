package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.ToggleActionButton
import com.intellij.ui.treeStructure.treetable.TreeTable
import eu.nyerel.panda.ijplugin.runner.PandaSettings

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class ToggleCallTreeAggregationButton : ToggleActionButton("Group by Method", IconLoader.findIcon("/actions/groupByMethod.png")) {

    override fun isSelected(e: AnActionEvent): Boolean {
        return PandaSettings.isAggregateCallTree
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        PandaSettings.isAggregateCallTree = state
        DrawCallTreeAction.drawInBackground(e.project,
                contextComponent as TreeTable)
    }

}
