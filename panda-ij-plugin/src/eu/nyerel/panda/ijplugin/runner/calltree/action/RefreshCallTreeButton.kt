package eu.nyerel.panda.ijplugin.runner.calltree.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.AnActionButton
import com.intellij.ui.treeStructure.treetable.TreeTable

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class RefreshCallTreeButton : AnActionButton("Refresh", IconLoader.findIcon("/actions/refresh.png")) {

    override fun actionPerformed(e: AnActionEvent) {
        DrawCallTreeAction.drawInBackground(e.project,
                contextComponent as TreeTable)
    }

}
