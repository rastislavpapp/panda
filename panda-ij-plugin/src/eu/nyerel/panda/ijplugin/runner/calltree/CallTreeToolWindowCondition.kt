package eu.nyerel.panda.ijplugin.runner.calltree

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Condition

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class CallTreeToolWindowCondition : Condition<Project> {

    override fun value(project: Project): Boolean {
        return true
    }

}
