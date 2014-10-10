package eu.nyerel.panda.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class DatabaseQuery extends MonitoredEvent {

	public DatabaseQuery(MonitoredEvent parent, String description, long duration) {
		super(parent, description);
		this.duration = duration;
	}

}
