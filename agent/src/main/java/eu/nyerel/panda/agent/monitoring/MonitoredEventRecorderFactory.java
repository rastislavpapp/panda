package eu.nyerel.panda.agent.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public enum MonitoredEventRecorderFactory implements MonitoringEventListener {

	INSTANCE;

	private final ThreadLocal<MonitoredEventRecorder> recorderTL = new ThreadLocal<MonitoredEventRecorder>();

    public MonitoredEventRecorder getRecorder() {
		ThreadLocal<MonitoredEventRecorder> callTreeRecorderTL = recorderTL;
		MonitoredEventRecorder cs = callTreeRecorderTL.get();
        if (cs == null) {
            cs = new MonitoredEventRecorder();
            callTreeRecorderTL.set(cs);
            return cs;
        } else {
            return cs;
        }
    }

	@Override
	public void onCallTreeFinished(MonitoredEvent node) {
		recorderTL.remove();
	}

}
