package eu.nyerel.panda.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public interface MonitoringEventListener {

	void onCallTreeFinished(MethodCall node);

}
