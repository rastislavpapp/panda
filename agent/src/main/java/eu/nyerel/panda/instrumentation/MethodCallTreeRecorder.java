package eu.nyerel.panda.instrumentation;

import eu.nyerel.panda.model.MethodNode;
import eu.nyerel.panda.monitoring.CallTreeRecorder;
import eu.nyerel.panda.monitoring.CallTreeRecorderFactory;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodCallTreeRecorder {

	private static long idSequence = 0;

	public static void enterMethod(String methodSignature) {
		CallTreeRecorder callTreeRecorder = CallTreeRecorderFactory.getRecorder();
		callTreeRecorder.startNode(new MethodNode(nextId(), callTreeRecorder.getCurrentNode(), methodSignature));
	}

	private static long nextId() {
		return idSequence++;
	}

	public static void exitMethod() {
		CallTreeRecorderFactory.getRecorder().finishCurrentNode();
	}

}
