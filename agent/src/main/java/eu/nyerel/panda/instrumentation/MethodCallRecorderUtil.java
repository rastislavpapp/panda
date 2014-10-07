package eu.nyerel.panda.instrumentation;

import eu.nyerel.panda.monitoring.MethodCall;
import eu.nyerel.panda.monitoring.MethodCallRecorder;
import eu.nyerel.panda.monitoring.MethodCallRecorderFactory;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodCallRecorderUtil {

	public static void startMethod(String methodSignature) {
		MethodCallRecorder methodCallRecorder = MethodCallRecorderFactory.getRecorder();
		methodCallRecorder.startMethod(new MethodCall(methodCallRecorder.getCurrentNode(), methodSignature));
	}

	public static void finishCurrentMethod() {
		MethodCallRecorderFactory.getRecorder().finishCurrentMethod();
	}

}
