package eu.nyerel.panda.agent.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodCall extends MonitoredEvent {

	private final long start = System.currentTimeMillis();

	public MethodCall(MonitoredEvent parent, String description) {
		super(parent, description);
	}

	public void finish() {
		super.finish();
		duration = System.currentTimeMillis() - start;
	}

	public long getStart() {
		return start;
	}

	@Override
	public String toString() {
		return getDescription() + " [" + duration + "ms]";
	}

}
