package eu.nyerel.panda.agent;

import eu.nyerel.panda.agent.util.Log;

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

    public static Log.Level getLogLevel() {
        String logLevel = System.getProperty("panda.log.level");
        if (logLevel != null && !logLevel.isEmpty()) {
            return Log.Level.valueOf(logLevel);
        } else {
            return null;
        }
    }

}
