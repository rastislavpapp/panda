package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaJavaProgramPatcher extends JavaProgramPatcher {

	@Override
	public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters) {
		if (executor.getId().equals(PandaExecutor.EXECUTOR_ID)) {
			System.out.println("ahoj");
		}
	}

}
