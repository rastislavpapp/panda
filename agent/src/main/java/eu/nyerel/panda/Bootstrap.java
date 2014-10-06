package eu.nyerel.panda;

import eu.nyerel.panda.instrumentation.MonitorClassFileTransformer;
import eu.nyerel.panda.monitoring.CallTreeRecorderFactory;
import eu.nyerel.panda.monitoring.MonitoringEventListenerRegistry;
import eu.nyerel.panda.monitoring.MonitoringService;

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
		MonitoringEventListenerRegistry.register(MonitoringService.getInstance());
		MonitoringEventListenerRegistry.register(CallTreeRecorderFactory.getInstance());
	}

}
