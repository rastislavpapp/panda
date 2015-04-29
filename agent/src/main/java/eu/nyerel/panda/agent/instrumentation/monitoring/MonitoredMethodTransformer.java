package eu.nyerel.panda.agent.instrumentation.monitoring;

import eu.nyerel.panda.agent.instrumentation.util.MethodBuildingUtil;
import eu.nyerel.panda.agent.instrumentation.AbstractMethodTransformer;
import eu.nyerel.panda.agent.instrumentation.util.MethodCallRecorderUtil;
import javassist.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitoredMethodTransformer extends AbstractMethodTransformer {

	private static final String CALL_TREE_RECORDER = MethodCallRecorderUtil.class.getName();

	@Override
	protected String createTransformedBody(CtMethod ctMethod) throws NotFoundException{
		MonitoredMethodBodyBuilder monitoredMethodBodyBuilder = new MonitoredMethodBodyBuilder();
		return monitoredMethodBodyBuilder.build(ctMethod);
	}

	@Override
	protected String uniqueTransformationName() {
		return "monitored";
	}

	private class MonitoredMethodBodyBuilder {

		private StringBuilder body;

		public String build(CtMethod ctMethod) throws NotFoundException {
			body = new StringBuilder();
			appendCallEnterMethod(ctMethod);
			body.append(MethodBuildingUtil.wrapTryFinally(getReturningInternalMethodCall(ctMethod), callExitMethod()));
			return body.toString();
		}

		private void appendCallEnterMethod(CtMethod ctMethod) {
			body.append(CALL_TREE_RECORDER).append(".startMethod(")
					.append("\"").append(ctMethod.getLongName()).append("\");\n");
		}

		private String callExitMethod() {
			return CALL_TREE_RECORDER + ".finishCurrentMethod();";
		}

	}

}
