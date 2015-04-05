package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.classFilter.ClassFilter;
import com.intellij.ui.classFilter.ClassFilterEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaSettingsEditor extends SettingsEditor<RunConfigurationBase> {

	private static final String PROPERTY_PANDA_MONITORED_CLASSES = "panda.monitored.classes";
	private final Project project;

	private ClassFilterEditor monitoredClassesEditor;
	private final JPanel mainPanel;

	public PandaSettingsEditor(Project project) {
		this.project = project;
		this.mainPanel = new JPanel(new GridBagLayout());
		this.monitoredClassesEditor = new ClassFilterEditor(project);
		this.monitoredClassesEditor.setClassDelimiter(".");
		this.monitoredClassesEditor.setBorder(IdeBorderFactory.createTitledBorder("Monitored classes", false));
		this.mainPanel.add(this.monitoredClassesEditor, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
	}

	@Override
	protected void resetEditorFrom(RunConfigurationBase s) {
		ClassFilter[] monitoredClasses = PandaSettings.getInstance().getMonitoredClasses();
		if (monitoredClasses == null) {
			PropertiesComponent properties = PropertiesComponent.getInstance(project);
			String[] monitoredClassPatterns = properties.getValues(PROPERTY_PANDA_MONITORED_CLASSES);
			List<ClassFilter> classFilterList = new ArrayList<ClassFilter>();
			if (monitoredClassPatterns != null) {
				for (String monitoredClassPattern : monitoredClassPatterns) {
					ClassFilter classFilter = new ClassFilter(monitoredClassPattern);
					classFilter.setEnabled(true);
					classFilterList.add(classFilter);
				}
			}
			monitoredClasses = classFilterList.toArray(new ClassFilter[classFilterList.size()]);
		}
		monitoredClassesEditor.setFilters(monitoredClasses);
	}

	@Override
	protected void applyEditorTo(RunConfigurationBase config) throws ConfigurationException {
		PandaSettings.getInstance().setMonitoredClasses(monitoredClassesEditor.getFilters());
		PropertiesComponent properties = PropertiesComponent.getInstance(project);
		properties.setValues(PROPERTY_PANDA_MONITORED_CLASSES, PandaSettings.getInstance().getMonitoredClassesArray());
	}

	@NotNull
	@Override
	protected JComponent createEditor() {
		return mainPanel;
	}

}
