package eu.nyerel.panda.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class MonitoredEventRecorderFactory implements MonitoringEventListener {

	private static MonitoredEventRecorderFactory INSTANCE;

	private final ThreadLocal<MonitoredEventRecorder> recorderTL =
			new ThreadLocal<MonitoredEventRecorder>();

    private MonitoredEventRecorderFactory() {
    }

	public static MonitoredEventRecorderFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MonitoredEventRecorderFactory();
		}
		return INSTANCE;
	}

    public static MonitoredEventRecorder getRecorder() {
		ThreadLocal<MonitoredEventRecorder> callTreeRecorderTL = getInstance().recorderTL;
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
