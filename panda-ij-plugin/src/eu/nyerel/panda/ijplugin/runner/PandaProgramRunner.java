package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.impl.DefaultJavaProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.execution.ui.RunContentDescriptor;
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
		} catch (Exception var4) {
			return false;
		}
	}

	@Override
	protected RunContentDescriptor doExecute(@NotNull RunProfileState state, @NotNull ExecutionEnvironment env) throws ExecutionException {
		return super.doExecute(state, env);
	}

	@NotNull
	public String getRunnerId() {
		return RUNNER_ID;
	}

}
