package eu.nyerel.panda.ijplugin.runner;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.classFilter.ClassFilter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum PandaSettings {

	INSTANCE;

	private static final String PROPERTY_PANDA_MONITORED_CLASSES = "panda.monitored.classes";

	public ClassFilter[] getMonitoredClasses(Project project) {
		PropertiesComponent properties = PropertiesComponent.getInstance(project);
		String[] classesProperty = properties.getValues(PROPERTY_PANDA_MONITORED_CLASSES);
		if (classesProperty != null) {
			return convertToMonitoredClasses(classesProperty);
		} else {
			return null;
		}
	}

	public String getMonitoredClassesAsString(Project project) {
		String[] monitoredClasses = convertToStringArray(getMonitoredClasses(project));
		StringBuilder sb = new StringBuilder();
		if (monitoredClasses != null) {
			for (int i = 0; i < monitoredClasses.length; i++) {
				sb.append(monitoredClasses[i]);
				if (i < monitoredClasses.length - 1) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}

	public void setMonitoredClasses(Project project, ClassFilter[] monitoredClasses) {
		PropertiesComponent properties = PropertiesComponent.getInstance(project);
		properties.setValues(PandaSettings.PROPERTY_PANDA_MONITORED_CLASSES, convertToStringArray(monitoredClasses));
	}

	@NotNull
	private ClassFilter[] convertToMonitoredClasses(String[] monitoredClassPatterns) {
		List<ClassFilter> classFilterList = new ArrayList<ClassFilter>();
		for (String monitoredClassPattern : monitoredClassPatterns) {
			ClassFilter classFilter = new ClassFilter(monitoredClassPattern);
			classFilter.setEnabled(true);
			classFilterList.add(classFilter);
		}
		return classFilterList.toArray(new ClassFilter[classFilterList.size()]);
	}

	private String[] convertToStringArray(ClassFilter[] monitoredClasses) {
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

}
