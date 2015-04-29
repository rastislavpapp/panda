package eu.nyerel.panda.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Agent {

	public static void premain(String args, Instrumentation inst) {
		new Bootstrap(inst).init();
	}

}
