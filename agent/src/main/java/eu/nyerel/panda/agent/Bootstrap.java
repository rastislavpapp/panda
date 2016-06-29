package eu.nyerel.panda.agent;

import eu.nyerel.panda.agent.instrumentation.monitoring.MonitorClassFileTransformer;
import eu.nyerel.panda.agent.monitoring.DataStorage;
import eu.nyerel.panda.agent.monitoring.MonitoredEventRecorderFactory;
import eu.nyerel.panda.agent.monitoring.MonitoringEventListenerRegistry;
import eu.nyerel.panda.agent.monitoringresult.MonitoringResultDumpWriter;
import eu.nyerel.panda.agent.monitoringresult.MonitoringResultServiceImpl;
import eu.nyerel.panda.agent.server.AgentServer;
import eu.nyerel.panda.agent.util.Log;

import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Bootstrap {

    private static final String[] FORBIDDEN_PACKAGES = new String[]{
            "java",
            "javax",
            "eu.nyerel.panda.agent"
    };

    private final Instrumentation inst;

    public Bootstrap(Instrumentation inst) {
        this.inst = inst;
    }

    public void init() {
        initLogLevel();
        Log.info("Initializing Panda monitoring...");
        List<String> monitoredClasses = Configuration.getMonitoredClasses();
        validateMonitoredClasses(monitoredClasses);
        Log.info("Monitored classes = {0}", monitoredClasses);
        inst.addTransformer(new MonitorClassFileTransformer());
        MonitoringEventListenerRegistry.register(MonitoringResultServiceImpl.INSTANCE);
        MonitoringEventListenerRegistry.register(MonitoredEventRecorderFactory.INSTANCE);
        MonitoringEventListenerRegistry.register(DataStorage.INSTANCE);

        AgentServer.INSTANCE.start();
        addCallTreeDumpShutdownHook();
        MonitoringResultDumpWriter.INSTANCE.clear();
    }

    private void addCallTreeDumpShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    MonitoringResultDumpWriter.INSTANCE.dumpCurrentResults();
                }
        }));
    }

    private void initLogLevel() {
        Log.Level level = Configuration.getLogLevel();
        if (level != null) {
            Log.setLevel(level);
        }
    }

    private void validateMonitoredClasses(List<String> monitoredClasses) {
        for (String monitoredClass : monitoredClasses) {
            for (String forbiddenPackage : FORBIDDEN_PACKAGES) {
                if (monitoredClass.startsWith(forbiddenPackage) || forbiddenPackage.startsWith(monitoredClass)) {
                    throw new IllegalArgumentException("Illegal monitored classes configuration - '" + monitoredClass + "'");
                }
            }
        }
    }

}
