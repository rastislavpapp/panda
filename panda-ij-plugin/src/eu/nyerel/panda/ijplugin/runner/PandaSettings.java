package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.classFilter.ClassFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaSettings {

	private static final String PROPERTY_PANDA_MONITORED_CLASSES = "panda.monitored.classes";

	private static PandaSettings INSTANCE;
	private ClassFilter[] monitoredClasses;
	private Project project;

	public static PandaSettings getInstance(RunConfigurationBase configuration) {
		if (INSTANCE == null) {
			Project project = configuration.getProject();
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
			INSTANCE = new PandaSettings();
			INSTANCE.monitoredClasses = classFilterList.toArray(new ClassFilter[classFilterList.size()]);
			INSTANCE.project = project;
		}
		return INSTANCE;
	}

	public ClassFilter[] getMonitoredClasses() {
		return monitoredClasses;
	}

	public String[] getMonitoredClassesArray() {
		if (monitoredClasses == null) {
			return null;
		} else {
			List<String> list = new ArrayList<String>();
			for (ClassFilter monitoredClass : monitoredClasses) {
				if (monitoredClass.isEnabled()) {
					list.add(monitoredClass.getPattern());
				}
			}
			return list.toArray(new String[list.size()]);
		}
	}

	public void setMonitoredClasses(ClassFilter[] monitoredClasses) {
		this.monitoredClasses = monitoredClasses;
		PropertiesComponent properties = PropertiesComponent.getInstance(project);
		properties.setValues(PandaSettings.PROPERTY_PANDA_MONITORED_CLASSES, getMonitoredClassesArray());
	}

}
