package eu.nyerel.panda.instrumentation.util;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class NamingUtil {

	public static String pandalize(String name) {
		return "__" + name + "__$PANDA";
	}

}
