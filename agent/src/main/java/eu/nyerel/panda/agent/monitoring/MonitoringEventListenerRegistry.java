package eu.nyerel.panda.agent.monitoring;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitoringEventListenerRegistry {

	private static final List<MonitoringEventListener> LISTENERS =
			new CopyOnWriteArrayList<MonitoringEventListener>();

	private MonitoringEventListenerRegistry() {
	}

	public static void register(MonitoringEventListener listener) {
		LISTENERS.add(listener);
	}

	public static void unregister(MonitoringEventListener listener) {
		LISTENERS.remove(listener);
	}

	public static void fireEventCallTreeFinished(MonitoredEvent node) {
		for (MonitoringEventListener listener : LISTENERS) {
			listener.onCallTreeFinished(node);
		}
	}

}
