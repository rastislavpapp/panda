package eu.nyerel.panda.ijplugin.runner;

import com.intellij.ui.classFilter.ClassFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaSettings {

	private static PandaSettings INSTANCE;
	private ClassFilter[] monitoredClasses;

	public static PandaSettings getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PandaSettings();
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
	}

}
