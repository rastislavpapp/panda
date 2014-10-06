package eu.nyerel.panda.monitoring;

import eu.nyerel.panda.model.CallTreeNode;
import eu.nyerel.panda.model.MonitoringEventListener;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class CallTreeRecorderFactory implements MonitoringEventListener {

	private static CallTreeRecorderFactory INSTANCE;

	private final ThreadLocal<CallTreeRecorder> callTreeRecorderTL =
			new ThreadLocal<CallTreeRecorder>();

    private CallTreeRecorderFactory() {
    }

	public static CallTreeRecorderFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CallTreeRecorderFactory();
		}
		return INSTANCE;
	}

    public static CallTreeRecorder getRecorder() {
		ThreadLocal<CallTreeRecorder> callTreeRecorderTL = getInstance().callTreeRecorderTL;
		CallTreeRecorder cs = callTreeRecorderTL.get();
        if (cs == null) {
            cs = new CallTreeRecorder();
            callTreeRecorderTL.set(cs);
            return cs;
        } else {
            return cs;
        }
    }

	@Override
	public void onCallTreeFinished(CallTreeNode node) {
		callTreeRecorderTL.remove();
	}

}
