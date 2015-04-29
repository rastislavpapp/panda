package eu.nyerel.panda.agent.instrumentation.util;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodBuildingUtil {

	public static String wrapTryFinally(String wrappedPart, String insideFinallyPart) {
		return "try {\n" + wrappedPart + "\n} finally {\n" + insideFinallyPart + "\n}";
	}

	public static String wrapBrackets(String string) {
		return "{\n" + string + "\n}\n";
	}

	public static String getCurrentTimeMillis() {
		return "System.currentTimeMillis()";
	}

}
