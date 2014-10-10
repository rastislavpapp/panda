package eu.nyerel.panda.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class MethodCallRecorderFactory implements MonitoringEventListener {

	private static MethodCallRecorderFactory INSTANCE;

	private final ThreadLocal<MonitoredEventRecorder> callTreeRecorderTL =
			new ThreadLocal<MonitoredEventRecorder>();

    private MethodCallRecorderFactory() {
    }

	public static MethodCallRecorderFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MethodCallRecorderFactory();
		}
		return INSTANCE;
	}

    public static MonitoredEventRecorder getRecorder() {
		ThreadLocal<MonitoredEventRecorder> callTreeRecorderTL = getInstance().callTreeRecorderTL;
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
		callTreeRecorderTL.remove();
	}

}
