package eu.nyerel.panda.instrumentation.util;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class NamingUtil {

	public static String pandalize(String name) {
		return pandalize(name, null);
	}

	public static String pandalize(String name, String suffix) {
		return "__" + name + "__$PANDA" + (suffix != null ? "_" + suffix : "");
	}

}
