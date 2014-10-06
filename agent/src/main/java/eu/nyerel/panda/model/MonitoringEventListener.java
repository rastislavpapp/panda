package eu.nyerel.panda.model;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public interface MonitoringEventListener {

	void onCallTreeFinished(CallTreeNode node);

}
