package eu.nyerel.panda;

import eu.nyerel.panda.instrumentation.jdbc.JdbcClassFileTransformer;
import eu.nyerel.panda.instrumentation.monitoring.MonitorClassFileTransformer;
import eu.nyerel.panda.monitoring.DataStorage;
import eu.nyerel.panda.monitoring.MonitoredEventRecorderFactory;
import eu.nyerel.panda.monitoring.MonitoringEventListenerRegistry;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;

import java.lang.instrument.Instrumentation;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Bootstrap {

	private final Instrumentation inst;

	public Bootstrap(Instrumentation inst) {
		this.inst = inst;
	}

	public void init() {
		inst.addTransformer(new MonitorClassFileTransformer());
		inst.addTransformer(new JdbcClassFileTransformer());
		MonitoringEventListenerRegistry.register(MonitoringResultService.getInstance());
		MonitoringEventListenerRegistry.register(MonitoredEventRecorderFactory.getInstance());
		MonitoringEventListenerRegistry.register(DataStorage.getInstance());
	}

}
