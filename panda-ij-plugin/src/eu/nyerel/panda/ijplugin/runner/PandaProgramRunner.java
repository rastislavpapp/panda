package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.impl.DefaultJavaProgramRunner;
import com.intellij.execution.process.CapturingProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ExecutionUtil;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.NotNull;

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
	protected RunContentDescriptor doExecute(@NotNull RunProfileState state, @NotNull ExecutionEnvironment env)
			throws ExecutionException {
		RunContentDescriptor descriptor = super.doExecute(state, env);
		if (descriptor != null) {
			ProcessHandler processHandler = descriptor.getProcessHandler();
			if (processHandler != null) {
				processHandler.addProcessListener(new CapturingProcessAdapter() {
					@Override
					public void processWillTerminate(ProcessEvent event, boolean willBeDestroyed) {
						if (AgentFacade.INSTANCE.isRunning()) {
							AgentFacade.INSTANCE.shutdown();
						}
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

}
