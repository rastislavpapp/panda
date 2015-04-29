package eu.nyerel.panda.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Configuration {

	public static List<String> getMonitoredClasses() {
		List<String> monitoredClasses = new ArrayList<String>();
		String monitoredClassesStr = System.getProperty("panda.monitored.classes");
		if (monitoredClassesStr != null && !monitoredClassesStr.isEmpty()) {
			Collections.addAll(monitoredClasses, monitoredClassesStr.split(","));
		}
		return monitoredClasses;
	}


}
