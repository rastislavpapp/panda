package eu.nyerel.panda.agent.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public interface MonitoringEventListener {

	void onCallTreeFinished(MonitoredEvent node);

}
