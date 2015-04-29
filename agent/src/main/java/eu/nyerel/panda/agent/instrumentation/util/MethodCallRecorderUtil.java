package eu.nyerel.panda.agent.instrumentation.util;

import eu.nyerel.panda.agent.monitoring.DatabaseQuery;
import eu.nyerel.panda.agent.monitoring.MethodCall;
import eu.nyerel.panda.agent.monitoring.MonitoredEventRecorder;
import eu.nyerel.panda.agent.monitoring.MonitoredEventRecorderFactory;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodCallRecorderUtil {

	public static void startMethod(String methodSignature) {
		MonitoredEventRecorder recorder = MonitoredEventRecorderFactory.INSTANCE.getRecorder();
		recorder.startEvent(new MethodCall(recorder.getCurrentNode(), methodSignature));
	}

	public static void finishCurrentMethod() {
		MonitoredEventRecorderFactory.INSTANCE.getRecorder().finishCurrentEvent();
	}

	public static void addQuery(String query, long duration) {
		MonitoredEventRecorder recorder = MonitoredEventRecorderFactory.INSTANCE.getRecorder();
		recorder.startEvent(new DatabaseQuery(recorder.getCurrentNode(), query, duration));
		recorder.finishCurrentEvent();
	}



}
