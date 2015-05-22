package eu.nyerel.panda.ijplugin.runner.calltree;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeToolWindowCondition implements Condition<Project> {

    @Override
    public boolean value(Project project) {
        return false;
    }

}
