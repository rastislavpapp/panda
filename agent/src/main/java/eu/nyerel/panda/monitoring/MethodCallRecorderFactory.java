package eu.nyerel.panda.monitoring;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class MethodCallRecorderFactory implements MonitoringEventListener {

	private static MethodCallRecorderFactory INSTANCE;

	private final ThreadLocal<MethodCallRecorder> callTreeRecorderTL =
			new ThreadLocal<MethodCallRecorder>();

    private MethodCallRecorderFactory() {
    }

	public static MethodCallRecorderFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MethodCallRecorderFactory();
		}
		return INSTANCE;
	}

    public static MethodCallRecorder getRecorder() {
		ThreadLocal<MethodCallRecorder> callTreeRecorderTL = getInstance().callTreeRecorderTL;
		MethodCallRecorder cs = callTreeRecorderTL.get();
        if (cs == null) {
            cs = new MethodCallRecorder();
            callTreeRecorderTL.set(cs);
            return cs;
        } else {
            return cs;
        }
    }

	@Override
	public void onCallTreeFinished(MethodCall node) {
		callTreeRecorderTL.remove();
	}

}
