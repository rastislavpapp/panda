package eu.nyerel.panda.agent.util;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class IdSequenceUtil {

	private static long NEXT_ID = 0;

	public static void reset() {
		NEXT_ID = 0;
	}

	public static long nextId() {
		return NEXT_ID++;
	}

}
