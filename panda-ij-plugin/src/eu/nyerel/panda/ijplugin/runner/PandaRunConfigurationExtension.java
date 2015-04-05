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
import org.jetbrains.annotations.Nullable;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaRunConfigurationExtension extends RunConfigurationExtension {

	@Nullable
	@Override
	protected SettingsEditor<RunConfigurationBase> createEditor(RunConfigurationBase configuration) {
		return new PandaSettingsEditor(configuration.getProject());
	}

	@Override
	public <T extends RunConfigurationBase> void updateJavaParameters(T configuration, JavaParameters params, RunnerSettings runnerSettings) throws ExecutionException {
		ParametersList vmParametersList = params.getVMParametersList();
		vmParametersList.add("-javaagent:C:\\panda.jar");
		vmParametersList.add("-Dpanda.monitored.classes=" + getMonitoredClasses());
	}

	private String getMonitoredClasses() {
		String[] monitoredClasses = PandaSettings.getInstance().getMonitoredClassesArray();
		StringBuilder monitoredClassesStr = new StringBuilder();
		if (monitoredClasses != null) {
			for (int i = 0; i < monitoredClasses.length; i++) {
				monitoredClassesStr.append(monitoredClasses[i]);
				if (i < monitoredClasses.length - 1) {
					monitoredClassesStr.append(",");
				}
			}
		}
		return monitoredClassesStr.toString();
	}

	@Override
	protected void readExternal(RunConfigurationBase runConfiguration, Element element) throws InvalidDataException {

	}

	@Override
	protected void writeExternal(RunConfigurationBase runConfiguration, Element element) throws WriteExternalException {

	}

	@Nullable
	@Override
	protected String getEditorTitle() {
		return "Panda";
	}

	@Override
	protected boolean isApplicableFor(RunConfigurationBase configuration) {
		if (configuration instanceof ApplicationConfiguration) {
			return false;
		} else {
			return true;
		}
	}

}
