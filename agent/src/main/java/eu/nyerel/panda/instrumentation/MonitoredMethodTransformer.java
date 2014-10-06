package eu.nyerel.panda.instrumentation;

import eu.nyerel.panda.instrumentation.util.NamingUtil;
import javassist.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitoredMethodTransformer {

	private final CtClass ctClass;
	private final CtMethod method;

	public MonitoredMethodTransformer(CtClass ctClass, CtMethod method) {
		this.ctClass = ctClass;
		this.method = method;
	}

	public void transform() throws NotFoundException, CannotCompileException {
		ctClass.addMethod(getCopy());
		MonitoredMethodBodyBuilder monitoredMethodBodyBuilder = new MonitoredMethodBodyBuilder();
		String body = monitoredMethodBodyBuilder.build();
		System.out.println("New method body:\n" + body);
		method.setBody(body);
	}

	private boolean returnsVoid() throws NotFoundException {
		return method.getReturnType().equals(CtClass.voidType);
	}

	private CtMethod getCopy() throws CannotCompileException {
		return CtNewMethod.copy(method, NamingUtil.pandalize(method.getName()), ctClass, null);
	}

	private static final String METHOD_CALL_TREE_RECORDER =
			MethodCallTreeRecorder.class.getName();

	private class MonitoredMethodBodyBuilder {

		private StringBuilder body;

		public String build() throws NotFoundException {
			body = new StringBuilder("{\n");
			callEnterMethod();
			openTry();
			callInternalMethod();
			closeTryAndOpenFinally();
			callExitMethod();
			closeFinally();
			body.append("}");
			return body.toString();
		}

		private void closeFinally() {
			body.append("\t}\n");
		}

		private void closeTryAndOpenFinally() {
			body.append("\t} finally {\n");
		}

		private void callInternalMethod() throws NotFoundException {
			if (!returnsVoid()) {
				body.append("\t\treturn ");
			} else {
				body.append("\t");
			}
			body.append(NamingUtil.pandalize(method.getName())).append("($$);\n");
		}

		private void openTry() {
			body.append("\ttry {\n");
		}

		private void callEnterMethod() {
			body.append("\t").append(METHOD_CALL_TREE_RECORDER).append(".enterMethod(")
					.append("\"").append(method.getLongName()).append("\");\n");
		}

		private void callExitMethod() {
			body.append("\t\t").append(METHOD_CALL_TREE_RECORDER).append(".exitMethod();\n");
		}

	}

}
