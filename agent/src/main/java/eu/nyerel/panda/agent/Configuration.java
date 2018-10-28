package eu.nyerel.panda.agent;

import eu.nyerel.panda.agent.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Configuration {

    private static List<String> monitoredClasses;
    private static List<String> excludedClasses;

    public static void init() {
        monitoredClasses = getClassPatternList("panda.monitored.classes");
        excludedClasses = getClassPatternList("panda.excluded.classes");
    }

    public static List<String> getMonitoredClasses() {
        return monitoredClasses;
    }

    public static List<String> getExcludedClasses() {
        return excludedClasses;
    }

    private static List<String> getClassPatternList(String property) {
        List<String> classes = new ArrayList<String>();
        String classesStr = System.getProperty(property);
        if (classesStr != null && !classesStr.isEmpty()) {
            Collections.addAll(classes, classesStr.split(","));
        }
        return classes;
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
