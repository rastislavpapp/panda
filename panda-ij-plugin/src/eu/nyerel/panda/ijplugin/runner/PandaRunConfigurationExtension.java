package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunConfigurationExtension;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.ui.classFilter.ClassFilter;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaRunConfigurationExtension extends RunConfigurationExtension {

	@Nullable
	@Override
	protected <P extends RunConfigurationBase> SettingsEditor<P> createEditor(@NotNull P configuration) {
		return new PandaSettingsEditor<P>(configuration.getProject());
	}

	@Override
	public <P extends RunConfigurationBase> void updateJavaParameters(P configuration, JavaParameters params, RunnerSettings runnerSettings) throws ExecutionException {
		ParametersList vmParametersList = params.getVMParametersList();
		vmParametersList.add("-javaagent:C:\\panda.jar");
		vmParametersList.add("-Dpanda.monitored.classes=" + getMonitoredClassesString(configuration));
	}

	@NotNull
	private <P extends RunConfigurationBase> String getMonitoredClassesString(P configuration) {
		return PandaSettings.INSTANCE.getMonitoredClassesAsString(configuration.getProject());
	}

	@Override
	protected void readExternal(@NotNull RunConfigurationBase runConfiguration, @NotNull Element element)
			throws InvalidDataException {

	}

	@Override
	protected void writeExternal(@NotNull RunConfigurationBase runConfiguration, @NotNull Element element)
			throws WriteExternalException {

	}

	@Nullable
	@Override
	protected String getEditorTitle() {
		return "Panda";
	}

	@Override
	protected boolean isApplicableFor(@NotNull RunConfigurationBase configuration) {
		if (configuration instanceof ApplicationConfiguration) {
			return false;
		} else {
			return true;
		}
	}

}
