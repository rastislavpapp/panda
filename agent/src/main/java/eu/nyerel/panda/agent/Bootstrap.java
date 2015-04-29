package eu.nyerel.panda.agent;

import eu.nyerel.panda.agent.instrumentation.jdbc.JdbcClassFileTransformer;
import eu.nyerel.panda.agent.instrumentation.monitoring.MonitorClassFileTransformer;
import eu.nyerel.panda.agent.monitoring.DataStorage;
import eu.nyerel.panda.agent.monitoring.MonitoredEventRecorderFactory;
import eu.nyerel.panda.agent.monitoring.MonitoringEventListenerRegistry;
import eu.nyerel.panda.agent.monitoringresult.MonitoringResultServiceImpl;

import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Bootstrap {

	private static final String[] FORBIDDEN_PACKAGES = new String[] {
			"java",
			"javax",
			"eu.nyerel.panda.agent"
	};

	private final Instrumentation inst;

	public Bootstrap(Instrumentation inst) {
		this.inst = inst;
	}

	public void init() {
		System.out.println("Initializing Panda monitoring...");
		List<String> monitoredClasses = Configuration.getMonitoredClasses();
		validateMonitoredClasses(monitoredClasses);
		System.out.println("Monitored classes = " + monitoredClasses);
		inst.addTransformer(new MonitorClassFileTransformer());
		inst.addTransformer(new JdbcClassFileTransformer());
		MonitoringEventListenerRegistry.register(MonitoringResultServiceImpl.INSTANCE);
		MonitoringEventListenerRegistry.register(MonitoredEventRecorderFactory.INSTANCE);
		MonitoringEventListenerRegistry.register(DataStorage.INSTANCE);

		MonitoringResultServiceImpl.INSTANCE.startRemote();
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
