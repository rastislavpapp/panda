package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.DefaultJavaProgramRunner;
import com.intellij.execution.process.CapturingProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowEP;
import com.intellij.openapi.wm.ex.ToolWindowManagerEx;
import eu.nyerel.panda.ijplugin.runner.calltree.PandaCallTreeWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaProgramRunner extends DefaultJavaProgramRunner {

    public static final String RUNNER_ID = "Panda Runner";

    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        try {
            return executorId.equals(PandaExecutor.EXECUTOR_ID)
                    && !(profile instanceof RunConfigurationWithSuppressedDefaultRunAction)
                    && profile instanceof RunConfigurationBase;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void patch(JavaParameters javaParameters, RunnerSettings settings, RunProfile runProfile, boolean beforeExecution) throws ExecutionException {
        super.patch(javaParameters, settings, runProfile, beforeExecution);
        if (beforeExecution) {
            ParametersList vmParametersList = javaParameters.getVMParametersList();
            vmParametersList.add("-javaagent:" + getAgentFilePath());
            Project project = ((RunConfigurationBase) runProfile).getProject();
            vmParametersList.add("-Dpanda.monitored.classes=" + getMonitoredClassesString(project));
        }
    }

    private String getAgentFilePath() {
        return JarUtils.geUnpackedResourcePath("/panda-agent.jar");
    }

    @NotNull
    private <P extends RunConfigurationBase> String getMonitoredClassesString(Project project) {
        return PandaSettings.INSTANCE.getMonitoredClassesAsString(project);
    }

    @Override
    protected RunContentDescriptor doExecute(@NotNull RunProfileState state, @NotNull final ExecutionEnvironment env)
            throws ExecutionException {
        RunContentDescriptor descriptor = super.doExecute(state, env);
        if (descriptor != null) {
            ProcessHandler processHandler = descriptor.getProcessHandler();
            if (processHandler != null) {
                processHandler.addProcessListener(new CapturingProcessAdapter() {

                    @Override
                    public void processWillTerminate(ProcessEvent event, boolean willBeDestroyed) {
                        AgentFacade.INSTANCE.shutdown();
                    }

                    @Override
                    public void startNotified(ProcessEvent event) {
                        registerPandaToolWindow(env.getProject());
                    }

                });
            }
        }
        return descriptor;
    }

    @NotNull
    public String getRunnerId() {
        return RUNNER_ID;
    }

    private void registerPandaToolWindow(Project project) {
        final ToolWindowManagerEx twm = ToolWindowManagerEx.getInstanceEx(project);
        final ToolWindow pandaToolWindow = twm.getToolWindow(PandaCallTreeWindow.PANDA_TOOL_WINDOW_ID);
        if (pandaToolWindow == null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ToolWindowEP[] toolWindows = Extensions.getExtensions(ToolWindowEP.EP_NAME);
                    for (ToolWindowEP toolWindow : toolWindows) {
                        if (toolWindow.id.equals(PandaCallTreeWindow.PANDA_TOOL_WINDOW_ID)) {
                            twm.initToolWindow(toolWindow);
                            twm.getToolWindow(PandaCallTreeWindow.PANDA_TOOL_WINDOW_ID).show(null);
                        }
                    }
                }
            });
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    pandaToolWindow.show(null);
                }
            });
        }
    }

}
